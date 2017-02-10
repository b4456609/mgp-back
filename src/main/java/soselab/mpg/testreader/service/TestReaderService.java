package soselab.mpg.testreader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soselab.mpg.testreader.model.ProviderReport;
import soselab.mpg.testreader.model.ServiceTestDetail;
import soselab.mpg.testreader.model.TestReport;
import soselab.mpg.testreader.repository.TestReportRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TestReaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestReaderService.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final TestReportRepository testReportRepository;

    public TestReaderService(TestReportRepository testReportRepository) {
        this.testReportRepository = testReportRepository;
    }

    public void saveServiceTest(Map<String, String> filenameAndContent) throws IOException {
        List<ProviderReport> providerReports = new ArrayList<>();
        for (String filename : filenameAndContent.keySet()) {
            if (filename.endsWith(".json")) {
                String serviceName = filename.substring(0, filename.lastIndexOf("."));
                String jsonReport = filenameAndContent.get(serviceName.concat(".json"));
                ServiceTestDetail serviceTestDetail = mapper.readValue(jsonReport, ServiceTestDetail.class);
                ProviderReport providerReport = new ProviderReport(serviceName, serviceTestDetail,
                        filenameAndContent.get(serviceName.concat(".md")));
                providerReports.add(providerReport);
            }
        }

        LOGGER.info("{}", providerReports);

        TestReport testReport = new TestReport(providerReports);
        testReportRepository.save(testReport);
    }
}
