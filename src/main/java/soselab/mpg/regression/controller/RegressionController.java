package soselab.mpg.regression.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.pact.service.PactService;
import soselab.mpg.regression.controller.dto.ConsummerDetail;
import soselab.mpg.regression.controller.dto.PactTestCaseDTO;
import soselab.mpg.regression.model.ConsumerProviderPair;
import soselab.mpg.regression.service.RegressionPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/regression")
public class RegressionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegressionController.class);
    private final RegressionPicker regressionPicker;
    private final GraphService graphService;
    private final PactService pactService;
    private final BDDService bddService;


    @Autowired
    public RegressionController(RegressionPicker regressionPicker, GraphService graphService, PactService pactService, BDDService bddService) {
        this.regressionPicker = regressionPicker;
        this.graphService = graphService;
        this.pactService = pactService;
        this.bddService = bddService;
    }

    @GetMapping("/serviceTest/{serviceName}")
    public List<PactTestCaseDTO> getRegressionServiceTest(@PathVariable("serviceName") String serviceName) {
        LOGGER.info(serviceName);
        List<PathGroup> pathNodeIdGroups = graphService.getPathNodeIdGroups();
        List<List<String>> paths = pathNodeIdGroups.stream()
                .flatMap(pathGroup -> pathGroup.getPaths().stream())
                .collect(Collectors.toList());
        LOGGER.info("path node {}", paths);
        List<ConsumerProviderPair> serviceTestPair = regressionPicker.getRegressionServiceTestPair(paths, serviceName);
        LOGGER.info("consumber provider pair {}", serviceTestPair);
        Map<String, List<String>> urls = pactService.getPactUrlByConsumerAndProvider(serviceTestPair);
        LOGGER.info("urls", urls);

        List<PactTestCaseDTO> pactTestCaseDTOS = new ArrayList<>();
        for (String s : urls.keySet()) {
            List<ConsummerDetail> consummerDetails = urls.get(s).stream()
                    .map(link -> new ConsummerDetail(link.split("/")[7], link))
                    .collect(Collectors.toList());
            pactTestCaseDTOS.add(new PactTestCaseDTO(s, consummerDetails));
        }
        return pactTestCaseDTOS;
    }

    @GetMapping("/uat/{serviceName}")
    public List<String> getScenarioAnnotations(@PathVariable("serviceName") String serviceName) {
        return bddService.getAllTagsRelateToService(serviceName);
    }
}
