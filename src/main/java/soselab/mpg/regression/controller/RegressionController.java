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
import soselab.mpg.regression.RegressionPicker;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.util.List;
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
    public List<String> getRegressionServiceTest(@PathVariable("serviceName") String serviceName) {
        LOGGER.info(serviceName);
        List<PathGroup> pathNodeIdGroups = graphService.getPathNodeIdGroups();
        List<List<String>> paths = pathNodeIdGroups.stream()
                .flatMap(pathGroup -> pathGroup.getPaths().stream())
                .collect(Collectors.toList());
        LOGGER.info("path node {}", paths);
        List<ConsumerProviderPair> serviceTestPair = regressionPicker.getRegressionServiceTestPair(paths, serviceName);
        LOGGER.info("consumber provider pair {}", serviceTestPair);
        List<String> urls = pactService.getPactUrlByConsumerAndProvider(serviceTestPair);
        return urls;
    }

    @GetMapping("/uat/{serviceName}")
    public List<String> getScenarioAnnotations(@PathVariable("serviceName") String serviceName) {
        return bddService.getAllTagsRelateToService(serviceName);
    }
}
