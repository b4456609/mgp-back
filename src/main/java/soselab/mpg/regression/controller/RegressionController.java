package soselab.mpg.regression.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.pact.service.PactService;
import soselab.mpg.regression.controller.dto.ConsumerDetail;
import soselab.mpg.regression.controller.dto.PactTestCaseDTO;
import soselab.mpg.regression.model.ConsumerProviderPair;
import soselab.mpg.regression.service.RegressionPicker;

import java.util.*;
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
    public List<List<PactTestCaseDTO>> getRegressionServiceTest(@PathVariable("serviceName") String serviceName,
                                                                @RequestParam(required = false, defaultValue = "5", value = "num") int num) {
        LOGGER.info(serviceName);
        List<List<String>> paths = getAllPath();
        LOGGER.info("path node {}", paths);
        List<ConsumerProviderPair> serviceTestPair = regressionPicker.getRegressionServiceTestPair(paths, serviceName);
        LOGGER.info("consumber provider pair {}", serviceTestPair);
        Map<String, List<String>> urls = pactService.getPactUrlByConsumerAndProvider(serviceTestPair);
        LOGGER.info("urls", urls);

        List<PactTestCaseDTO> pactTestCaseDTOS = new ArrayList<>();
        for (String s : urls.keySet()) {
            List<ConsumerDetail> consumerDetails = urls.get(s).stream()
                    .map(link -> new ConsumerDetail(link.split("/")[7], link))
                    .collect(Collectors.toList());
            pactTestCaseDTOS.add(new PactTestCaseDTO(s, consumerDetails));
        }
        List<List<PactTestCaseDTO>> result = getLists(pactTestCaseDTOS, num);
        return result;
    }

    private <T> List<List<T>> getLists(List<T> pactTestCaseDTOS, int num) {
        List<List<T>> result = new ArrayList<>();

        for (int i = 0; i < pactTestCaseDTOS.size(); i++) {
            if (i + num < pactTestCaseDTOS.size()) {
                List<T> temp = pactTestCaseDTOS.subList(i, i + num);
                result.add(temp);
                i += num;
            }
            else {
                List<T> temp = pactTestCaseDTOS.subList(i, pactTestCaseDTOS.size());
                result.add(temp);
                break;
            }
        }
        return result;
    }

    private List<List<String>> getAllPath() {
        List<PathGroup> pathNodeIdGroups = graphService.getPathNodeIdGroups();
        return pathNodeIdGroups.stream()
                .flatMap(pathGroup -> pathGroup.getPaths().stream())
                .collect(Collectors.toList());
    }

    @GetMapping("/uat/{serviceName}")
    public List<List<String>> getScenarioAnnotations(@PathVariable("serviceName") String serviceName,
                                                     @RequestParam(required = false, defaultValue = "5", value = "num") int num) {
        List<List<String>> paths = getAllPath();
        List<String> scenarioAnnotations = regressionPicker.getScenarioAnnotations(paths, serviceName);
        List<String> tag = bddService.getTag(scenarioAnnotations);
        return getLists(tag, num);
    }
}
