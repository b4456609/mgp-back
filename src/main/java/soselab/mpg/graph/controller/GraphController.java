package soselab.mpg.graph.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.NoBDDProjectGitSettingException;
import soselab.mpg.graph.controller.dto.EndpointInformationDTO;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.controller.dto.ServiceCallInformationDTO;
import soselab.mpg.graph.controller.dto.ServiceInformationDTO;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.pact.model.ServiceCallRelationInformation;
import soselab.mpg.pact.service.PactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/graph")
public class GraphController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphController.class);

    private final GraphService graphService;
    private final PactService pactService;
    private final BDDService bddService;

    @Autowired
    public GraphController(GraphService graphService, PactService pactService, BDDService bddService) {
        this.graphService = graphService;
        this.pactService = pactService;
        this.bddService = bddService;
    }

    @GetMapping("/visual")
    public GraphDataDTO getGraphData() {
        LOGGER.info("Get graph data");
        // check bdd
        try {
            bddService.parseProject();
        } catch (NoBDDProjectGitSettingException e) {
            LOGGER.info("{}", e);
        }
        // get visual data for d3
        return graphService.getVisualizationData();
    }

    @GetMapping("/service")
    public List<ServiceInformationDTO> getServiceInformation() {
        return graphService.getServiceInfo();
    }

    @GetMapping("/serviceCall")
    public List<ServiceCallInformationDTO> getServiceCallInformation() {
        //TODO find a suitable to call the pact broker
        //get latest pact file from pact broker
        pactService.getLatestPactFile();

        List<ServiceCallInformationDTO> serviceCallInformationDTOS = graphService.getProviderConsumerPair();

        List<ServiceCallRelationInformation> serviceCallRelationInformations = pactService.getPacts();

        //if no pact file return directly
        if (serviceCallRelationInformations.isEmpty())
            return serviceCallInformationDTOS;

        //match graph data and pact data
        serviceCallInformationDTOS.forEach(serviceCallInformationDTO -> {
            Optional<ServiceCallRelationInformation> serviceCallRelationInformation1 = serviceCallRelationInformations.stream().filter(serviceCallRelationInformation -> {
                return serviceCallInformationDTO.getConsumer().equals(serviceCallRelationInformation.getConsumer()) &&
                        serviceCallInformationDTO.getProvider().equals(serviceCallRelationInformation.getProvider());
            }).findAny();
            if (serviceCallRelationInformation1.isPresent()) {
                serviceCallInformationDTO.setPact(serviceCallRelationInformation1.get().getPact());
            }
        });

        return serviceCallInformationDTOS;
    }

    @GetMapping("/endpoint")
    public List<EndpointInformationDTO> getEndpointInformation() {
        return graphService.getEndpointInformations();
    }
}
