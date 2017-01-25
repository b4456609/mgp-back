package soselab.mpg.graph.service;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.codegen.client.CodeGenClient;
import soselab.mpg.codegen.model.CodeSnippet;
import soselab.mpg.graph.controller.dto.*;
import soselab.mpg.graph.controller.dto.factory.GraphVisualizationFromGraphFactory;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.service.MPDService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraphServiceImpl implements GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphServiceImpl.class);

    private final GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory;
    private final MPDService mpdService;
    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;
    private final CodeGenClient codeGenClient;

    @Autowired
    public GraphServiceImpl(GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory,
                            MPDService mpdService, ServiceNodeRepository serviceNodeRepository,
                            EndpointNodeRepository endpointNodeRepository, CodeGenClient codeGenClient) {
        this.graphVisualizationFromGraphFactory = graphVisualizationFromGraphFactory;
        this.mpdService = mpdService;
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.codeGenClient = codeGenClient;
    }

    @Override
    public GraphDataDTO getVisualizationData() {
        //endpoint node
        Iterable<EndpointNode> endpointNodes = endpointNodeRepository.findAll();

        //service node
        Iterable<ServiceNode> serviceNodes = serviceNodeRepository.findAll();

        //Service and Endpoint relationship
        List<ServiceWithEndpointPairItem> allServiceWithEndpoint = serviceNodeRepository
                .getAllServiceWithEndpoint();

        //ProviderEndpoint Consumer relationship
        List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair = endpointNodeRepository
                .getProviderEndpointWithConsumerPairPair();

        List<List<String>> pathNodeIdGroups = getPathNodeIdGroups();

        return graphVisualizationFromGraphFactory.create(endpointNodes, serviceNodes, allServiceWithEndpoint, providerEndpointWithConsumerPairPair, pathNodeIdGroups);
    }

    @Override
    public List<List<String>> getPathNodeIdGroups() {
        PathAnalyzer pathAnalyzer = new PathAnalyzer(serviceNodeRepository, endpointNodeRepository);
        return pathAnalyzer.getPathNodeIdGroups();
    }


    @Override
    public List<ServiceInformationDTO> getServiceInfo() {

        //get service node service call count and service endpoint count
        List<ServiceInformationDTO> serviceInformationDTOS = serviceNodeRepository.getServiceInfo();

        return serviceInformationDTOS;
    }

    @Override
    public List<EndpointInformationDTO> getEndpointInformations() {
        Iterable<EndpointNode> all = endpointNodeRepository.findAll();
        List<EndpointNode> endpointNodes = IteratorUtils.toList(all.iterator());
        LOGGER.info("Endpoint information enpoint {}", endpointNodes.toString());
        List<EndpointInformationDTO> endpointInformationDTOS = endpointNodes.stream()
                .map(endpointNode -> {
                    String path = endpointNode.getPath();
                    String httpMethod = endpointNode.getHttpMethod();
                    CodeSnippet codeSnippet = codeGenClient.getCodeSnippet(httpMethod, path);
                    return new EndpointInformationDTO(endpointNode.getEndpointId(), httpMethod, path, codeSnippet);
                })
                .collect(Collectors.toList());
        return endpointInformationDTOS;
    }


}
