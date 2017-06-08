package soselab.mpg.graph.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.graph.controller.dto.*;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;
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
    private final MicroserviceGraphBuilderService microserviceGraphBuilderService;

    @Autowired
    public GraphController(GraphService graphService, PactService pactService, BDDService bddService,
                           MicroserviceGraphBuilderService microserviceGraphBuilderService) {
        this.graphService = graphService;
        this.pactService = pactService;
        this.bddService = bddService;
        this.microserviceGraphBuilderService = microserviceGraphBuilderService;
    }

    @ApiOperation(value = "Get D3.js Data")
    @GetMapping("/visual")
    public GraphDataDTO getGraphData() {
        // get visual data for d3
        return graphService.getVisualizationData(null, null);
    }

    @ApiOperation(value = "Get Service information")
    @GetMapping("/service")
    public List<ServiceInformationDTO> getServiceInformation() {
        return graphService.getServiceInfo();
    }

    @ApiOperation(value = "Get Service Call information")
    @GetMapping("/serviceCall")
    public List<ServiceCallInformationDTO> getServiceCallInformation() {

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

    @ApiOperation(value = "Get Endpoint Information")
    @GetMapping("/endpoint")
    public List<EndpointInformationDTO> getEndpointInformation() {
        return graphService.getEndpointInformations();
    }

    @ApiOperation(value = "Get Scenario Information")
    @GetMapping("/scenario")
    public ScenarioInformationDTO getScenarioInforamtionDTO() {
        return bddService.getScenarioInfomation();
    }
}
