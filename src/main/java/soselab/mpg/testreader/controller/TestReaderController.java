package soselab.mpg.testreader.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soselab.mpg.testreader.controller.dto.FileNameExtractor;
import soselab.mpg.testreader.controller.dto.ReportDTO;
import soselab.mpg.testreader.controller.dto.UATDTO;
import soselab.mpg.testreader.controller.exception.FileTypeNotCorrectException;
import soselab.mpg.testreader.controller.exception.ProccessFailException;
import soselab.mpg.testreader.service.TestReaderService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/test")
public class TestReaderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestReaderController.class);
    private final TestReaderService testReaderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public TestReaderController(TestReaderService testReaderService) {
        this.testReaderService = testReaderService;
    }

    @PostMapping("/uat")
    public void uploadUatTest(@RequestParam("files") MultipartFile[] uploadingFiles) {
        try {
            List<UATDTOAndRunNumber> uatdtoAndRunNumbers = Stream.of(uploadingFiles)
                    .flatMap(file -> {
                        try {
                            byte[] content = file.getBytes();
                            List<UATDTO> uatdtos = objectMapper.readValue(content, new TypeReference<List<UATDTO>>() {
                            });
                            return Stream.of(new UATDTOAndRunNumber(
                                    FileNameExtractor.getRunNumber(file.getOriginalFilename()),
                                    uatdtos, content));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Stream.empty();
                    })
                    .collect(Collectors.toList());

            testReaderService.saveUATTest(uatdtoAndRunNumbers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/serviceTest")
    public void uploadTest(@RequestParam("files") MultipartFile[] uploadingFiles) {
        LOGGER.info("recevice files{}", Arrays.toString(uploadingFiles));
        //validation
        if (uploadingFiles.length == 0) {
            throw new NoFilesException();
        }
        Map<String, String> filenameAndContent = new HashMap<>();
        try {
            for (MultipartFile uploadingFile : uploadingFiles) {
                String originalFilename = uploadingFile.getOriginalFilename();
                if (!originalFilename.endsWith(".md") && !originalFilename.endsWith(".json"))
                    throw new FileTypeNotCorrectException();
                String content = new String(uploadingFile.getBytes());
                filenameAndContent.put(originalFilename, content);
            }
            LOGGER.info("file and content map{}", filenameAndContent);
            testReaderService.saveServiceTest(filenameAndContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProccessFailException();
        }
    }

    @GetMapping("/report")
    public Page<ReportDTO> getReports(@PageableDefault(value = 5, sort = {"createdDate"}, direction = Sort.Direction.DESC)
                                              Pageable pageable) {
        return testReaderService.getReports(pageable);
    }

    @GetMapping(path = "/raw/serviceTest/{timestamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getServiceTest(@PathVariable("timestamp") String timestamp) {
        long time = Long.valueOf(timestamp);
        String serviceTestRawContentByTimestamp = testReaderService.getServiceTestRawContentByTimestamp(time);
        LOGGER.debug(serviceTestRawContentByTimestamp);
        return serviceTestRawContentByTimestamp;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No files found")
    private class NoFilesException extends RuntimeException {
    }
}
