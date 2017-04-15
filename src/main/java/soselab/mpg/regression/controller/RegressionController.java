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
            List<ConsumerDetail> consumerDetails = urls.get(s).stream()
                    .map(link -> new ConsumerDetail(link.split("/")[7], link))
                    .collect(Collectors.toList());
            pactTestCaseDTOS.add(new PactTestCaseDTO(s, consumerDetails));
        }
        List<List<PactTestCaseDTO>> result = new ArrayList<>();

        for (int i = 0; i < pactTestCaseDTOS.size(); i++) {
            if (i + 5 < pactTestCaseDTOS.size()) {
                List<PactTestCaseDTO> temp = pactTestCaseDTOS.subList(i, i + 5);
                result.add(temp);
                i += 5;
            }
            else {
                List<PactTestCaseDTO> temp = pactTestCaseDTOS.subList(i, pactTestCaseDTOS.size());
                result.add(temp);
                break;
            }
        }
        return result;
    }

    @GetMapping("/uat/{serviceName}")
    public List<String> getScenarioAnnotations(@PathVariable("serviceName") String serviceName) {
        return bddService.getAllTagsRelateToService(serviceName);
    }
}
