package soselab.mpg.testreader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.testreader.controller.UATDTOAndRunNumber;
import soselab.mpg.testreader.controller.dto.FileNameExtractor;
import soselab.mpg.testreader.controller.dto.ReportDTO;
import soselab.mpg.testreader.controller.exception.NotFoundException;
import soselab.mpg.testreader.model.*;
import soselab.mpg.testreader.repository.TestReportRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TestReaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestReaderService.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final TestReportRepository testReportRepository;
    private final GraphService graphService;

    public TestReaderService(TestReportRepository testReportRepository, GraphService graphService) {
        this.testReportRepository = testReportRepository;
        this.graphService = graphService;
    }

    public void saveServiceTest(Map<String, String> filenameAndContent) throws IOException {
        Map<String, Set<String>> consumerProviderMap = new HashMap<>();
        List<DetailReport> providerReports = new ArrayList<>();
        for (String filename : filenameAndContent.keySet()) {
            if (filename.endsWith(".json")) {
                String serviceName = FileNameExtractor.getServiceName(filename);
                String jsonReport = filenameAndContent.get(FileNameExtractor.getFileNameWithoutType(filename)
                        .concat(".json"));
                int runNumber = FileNameExtractor.getRunNumber(filename);
                ServiceTestDetail serviceTestDetail = mapper.readValue(jsonReport, ServiceTestDetail.class);

                //set fail test consumer and provider to map
                long failCount = serviceTestDetail.getExecution().stream()
                        .flatMap(executionBean -> {
                            String consumerName = executionBean.getConsumer().getName();
                            return executionBean.getInteractions().stream()
                                    .filter(excution -> {
                                        if (excution.getVerification().getResult().equals("failed")) {
                                            String method = excution.getInteraction().getRequest().getMethod();
                                            String path = excution.getInteraction().getRequest().getPath();
                                            String endpointId = String.format("%s %s %s %s", serviceName, "endpoint", path, method);
                                            Set<String> provider = consumerProviderMap.get(consumerName);
                                            if (provider == null) {
                                                HashSet<String> set = new HashSet<>();
                                                set.add(endpointId);
                                                consumerProviderMap.put(consumerName, set);
                                            } else {
                                                provider.add(endpointId);
                                            }
                                            return true;
                                        }
                                        return false;
                                    });
                        }).count();

                ProviderReport providerReport = new ProviderReport(serviceName, serviceTestDetail,
                        filenameAndContent.get(serviceName.concat(".md")), failCount, runNumber);
                providerReports.add(providerReport);
            }
        }

        LOGGER.info("{}", providerReports);
        LOGGER.info("consumerProviderMap {}", consumerProviderMap);

        //get d3 snapshot visual data
        GraphDataDTO visualizationData = graphService.getVisualizationData(consumerProviderMap, null);
        String json = mapper.writeValueAsString(visualizationData);
        LOGGER.info("Get visulaiztion data", visualizationData);

        //add all raw report
        ArrayList<String> rawReports = new ArrayList<>();
        rawReports.addAll(filenameAndContent.values());

        TestReport testReport = new TestReport("service", providerReports, json, rawReports);
        testReportRepository.save(testReport);
    }

    public Page<ReportDTO> getReports(Pageable pageable) {
        Page<TestReport> all = testReportRepository.findAll(pageable);
        Page<ReportDTO> reportDTOS = all.map(testReport -> {
            List<ReportDTO.ReportBean> report = testReport.getTestReports().stream()
                    .map(providerReport -> {
                        return new ReportDTO.ReportBean(providerReport.getName(),
                                providerReport.getFailCount(), providerReport.getReport());
                    })
                    .collect(Collectors.toList());
            return new ReportDTO(testReport.getCreatedDate().longValue(), testReport.getType(),
                    testReport.getVisualData(), report);
        });
        return reportDTOS;
    }

    public String getServiceTestRawContentByTimestamp(long time) {
        TestReport testReport = testReportRepository.findOneByCreatedDate(time);
        if (testReport == null)
            throw new NotFoundException();
        return testReport.getRawReports().get(0);
    }

    public void saveUATTest(List<UATDTOAndRunNumber> uatdtoAndRunNumbers) throws JsonProcessingException {
        List<DetailReport> scenarioReports = uatdtoAndRunNumbers.stream()
                .flatMap(item -> item.getUatdtos().stream()
                        .flatMap(uatdto -> uatdto.getElements().stream()
                                .flatMap(elementsBean -> {
                                    if (elementsBean.getType().equals("scenario")) {
                                        String scenarioName = elementsBean.getName();
                                        long failedCount = elementsBean.getSteps().stream()
                                                .filter(stepsBean ->
                                                        stepsBean.getResult().getStatus().equals("failed")
                                                ).count();
                                        return Stream.of(new ScenarioReport(scenarioName, failedCount, item.getRunNumber()));
                                    }
                                    return Stream.empty();
                                }))
                ).collect(Collectors.toList());
        Set<String> failedScenario = scenarioReports.stream()
                .filter(report -> report.getFailCount() > 0)
                .map(DetailReport::getName)
                .collect(Collectors.toSet());
        LOGGER.info("failed scenario {}", failedScenario);

        GraphDataDTO visualizationData = graphService.getVisualizationData(null, failedScenario);
        String json = mapper.writeValueAsString(visualizationData);

        List<String> contents = uatdtoAndRunNumbers.stream()
                .map(UATDTOAndRunNumber::getContent)
                .map(String::new)
                .collect(Collectors.toList());

        TestReport testReport = new TestReport("uat", scenarioReports, json,
                contents);
        testReportRepository.save(testReport);

    }
}
