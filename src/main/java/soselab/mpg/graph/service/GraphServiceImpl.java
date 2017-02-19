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
import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.model.ScenarioNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ScenarioNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphServiceImpl implements GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphServiceImpl.class);

    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;
    private final CodeGenClient codeGenClient;
    private final ScenarioNodeRepository scenarioNodeRepository;

    @Autowired
    public GraphServiceImpl(ServiceNodeRepository serviceNodeRepository,
                            EndpointNodeRepository endpointNodeRepository, CodeGenClient codeGenClient,
                            ScenarioNodeRepository scenarioNodeRepository) {
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.codeGenClient = codeGenClient;
        this.scenarioNodeRepository = scenarioNodeRepository;
    }

    @Override
    public GraphDataDTO getVisualizationData(Map<String, Set<String>> errorMarkConsumerAndProvider, Set<String> failedScenario) {

        long start = System.currentTimeMillis();

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

        List<PathGroup> pathNodeIdGroups = getPathNodeIdGroups();

        setCyclicGroups(pathNodeIdGroups);

        //get Scenario node
        Iterable<ScenarioNode> scenarioNodes = scenarioNodeRepository.findAll();

        long time = System.currentTimeMillis() - start;

        GraphDataDTO graphDataDTO = GraphVisualizationFromGraphFactory.create(endpointNodes, serviceNodes, allServiceWithEndpoint,
                providerEndpointWithConsumerPairPair, pathNodeIdGroups, scenarioNodes, errorMarkConsumerAndProvider,
                failedScenario);

        long finish = System.currentTimeMillis() - start;

        LOGGER.info("Generate garph data execution time: db query {}ms, db + gen data {}ms", time, finish);

        return graphDataDTO;
    }

    @Override
    public List<PathGroup> getPathNodeIdGroups() {
        PathAnalyzer pathAnalyzer = new PathAnalyzer(endpointNodeRepository);
        return pathAnalyzer.getPathNodeIdGroups();
    }

    @Override
    public void setCyclicGroups(List<PathGroup> pathNodeIdGroups) {
        CyclicAnalyzer.analyze(pathNodeIdGroups);
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

    @Override
    public List<ServiceCallInformationDTO> getProviderConsumerPair() {
        return serviceNodeRepository.getConsumerProvider();
    }


}
