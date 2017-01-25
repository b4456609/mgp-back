package soselab.mpg.graph.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.graph.controller.dto.EndpointInformationDTO;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.controller.dto.ServiceCallInformationDTO;
import soselab.mpg.graph.controller.dto.ServiceInformationDTO;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.service.PactService;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/graph")
public class GraphController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphController.class);

    private final GraphService graphService;
    private final PactService pactService;
    private final ModelMapper modelMapper;

    @Autowired
    public GraphController(GraphService graphService, PactService pactService, ModelMapper modelMapper) {
        this.graphService = graphService;
        this.pactService = pactService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/visual")
    public GraphDataDTO getGraphData() {
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

        //service call information
        List<Pact> pacts = pactService.getPacts();
        Type targetListType = new TypeToken<List<ServiceCallInformationDTO>>() {
        }.getType();
        return modelMapper.map(pacts, targetListType);
    }

    @GetMapping("/endpoint")
    public List<EndpointInformationDTO> getEndpointInformation() {
        return graphService.getEndpointInformations();
    }
}
