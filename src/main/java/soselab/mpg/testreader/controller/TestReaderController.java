package soselab.mpg.testreader.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soselab.mpg.testreader.service.TestReaderService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestReaderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestReaderController.class);
    private final TestReaderService testReaderService;

    @Autowired
    public TestReaderController(TestReaderService testReaderService) {
        this.testReaderService = testReaderService;
    }

    @PostMapping("/serviceTest")
    public void uploadTest(@RequestParam("files") MultipartFile[] uploadingFiles) {
        LOGGER.info("recevice files{}", uploadingFiles);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            LOGGER.info("file and content map{}", filenameAndContent);
            testReaderService.saveServiceTest(filenameAndContent);
        } catch (IOException e) {
            throw new ProccessFailException();
        }
    }

    @GetMapping("/serviceTest")
    public Page<ReportDTO> getReports(@PageableDefault(value = 5, sort = {"timestamp"}, direction = Sort.Direction.DESC)
                                              Pageable pageable) {
        return testReaderService.getReports(pageable);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No files found")
    private class NoFilesException extends RuntimeException {
    }
}
