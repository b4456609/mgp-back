package soselab.mpg.testreader.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.testreader.controller.UATDTO;
import soselab.mpg.testreader.model.TestReport;
import soselab.mpg.testreader.repository.TestReportRepository;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestReaderServiceTest {

    @Autowired
    private TestReaderService testReaderService;

    @MockBean
    private TestReportRepository testReportRepository;

    @Captor
    private ArgumentCaptor<TestReport> testReportArgumentCaptor;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveServiceTest() throws Exception {
        URL jsonResource = this.getClass().getResource("/serviceTest/easylearn_note.json");
        System.out.println(jsonResource);
        URL markdownResource = this.getClass().getResource("/serviceTest/easylearn_note.md");
        String json = new String(Files.readAllBytes(Paths.get(jsonResource.toURI())));
        String markdown = new String(Files.readAllBytes(Paths
                .get(markdownResource.toURI())));

        Map<String, String> filenameContentMap = new HashMap<>();
        filenameContentMap.put("easylearn_note.json", json);
        filenameContentMap.put("easylearn_note.md", markdown);

        testReaderService.saveServiceTest(filenameContentMap);

        verify(testReportRepository).save(testReportArgumentCaptor.capture());
        TestReport value = testReportArgumentCaptor.getValue();
        System.out.println(value);
    }

    @Test
    public void testUatTest() throws Exception {
        URL jsonResource = this.getClass().getResource("/uat.json");
        byte[] content = Files.readAllBytes(Paths.get(jsonResource.toURI()));
        List<UATDTO> uatdtos = objectMapper.readValue(content, new TypeReference<List<UATDTO>>() {
        });
        testReaderService.saveUATTest(content, uatdtos);
        verify(testReportRepository).save(testReportArgumentCaptor.capture());
        TestReport value = testReportArgumentCaptor.getValue();
        System.out.println(value);
    }

}