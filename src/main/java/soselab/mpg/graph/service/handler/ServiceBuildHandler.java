package soselab.mpg.graph.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.controller.dto.factory.EndpointIdFactory;
import soselab.mpg.graph.model.CallRelationship;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.CallRelationshipRepository;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.model.Endpoint2ServiceCallDependency;
import soselab.mpg.mpd.model.IDExtractor;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.model.ServiceCall;
import soselab.mpg.mpd.service.MPDService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ServiceBuildHandler implements GraphBuildHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBuildHandler.class);

    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;
    private final CallRelationshipRepository callRelationshipRepository;
    private final MPDService mpdService;
    private ConcurrentHashMap<String, EndpointNode> allEndpointNodesMap;
    private List<CallRelationship> callRelationshipList;
    private List<EndpointNode> endpointNodes;

    @Autowired
    public ServiceBuildHandler(ServiceNodeRepository serviceNodeRepository, EndpointNodeRepository endpointNodeRepository
            , CallRelationshipRepository callRelationshipRepository, MPDService mpdService) {
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.callRelationshipRepository = callRelationshipRepository;
        this.mpdService = mpdService;
    }

    @Override
    public void build() {
        this.callRelationshipList = new ArrayList<>();
        this.allEndpointNodesMap = new ConcurrentHashMap<String, EndpointNode>();
        this.endpointNodes = new ArrayList<>();

        LOGGER.info("build service and endpoint Graph");
        endpointNodeRepository.deleteAll();
        serviceNodeRepository.deleteAll();
        callRelationshipRepository.deleteAll();

        List<MicroserviceProjectDescription> microserviceProjectDescriptions = mpdService
                .getMicroserviceProjectDescriptions();

        LOGGER.debug("mpd size{}", microserviceProjectDescriptions.size());

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(this::createServiceNode)
                .collect(Collectors.toSet());

        // create endpoint service call relationship
        List<EndpointNode> endpointNodeList = microserviceProjectDescriptions.stream()
                .flatMap(this::createEndpointRelation)
                .collect(Collectors.toList());
        LOGGER.debug("endpointNodes size: {}, content: {}", endpointNodes.size(), endpointNodes);
        LOGGER.debug("service node size: {}, content: {}", serviceNodes.size(), serviceNodes);
        LOGGER.debug("endpointNodes node size: {}, content: {}", endpointNodes.size(), endpointNodes);
        LOGGER.debug("call realtionship size: {}, content: {}", callRelationshipList.size(), callRelationshipList);

        //save to database
        serviceNodeRepository.save(serviceNodes);
        endpointNodeRepository.save(endpointNodes);
    }

    private ServiceNode createServiceNode(MicroserviceProjectDescription microserviceProjectDescription) {
        // generate service's endpoint nodes
        Set<EndpointNode> endpointNodes = microserviceProjectDescription.getEndpoint().stream()
                .map(endpoint -> {
                    String path = endpoint.getPath();
                    String method = endpoint.getMethod();
                    String name = microserviceProjectDescription.getName();
                    String id = EndpointIdFactory.getId(name, path, method);
                    EndpointNode endpointNode = new EndpointNode(id, path, method);

                    //add to all endpoint for call realtionship
                    allEndpointNodesMap.putIfAbsent(id, endpointNode);
                    return endpointNode;
                }).collect(Collectors.toSet());
        this.endpointNodes.addAll(endpointNodes);

        // return a new service node
        return new ServiceNode(microserviceProjectDescription.getName(), endpointNodes);
    }

    private Stream<EndpointNode> createEndpointRelation(MicroserviceProjectDescription microserviceProjectDescription) {
        Map<String, Boolean> serviceCallIsTest = microserviceProjectDescription.getServiceCall().stream()
                .collect(Collectors.toMap(ServiceCall::getId, ServiceCall::isUnTest));
        List<Endpoint2ServiceCallDependency> endpoint2ServiceCallDependency = microserviceProjectDescription.getEndpointDep();
        return endpoint2ServiceCallDependency.stream()
                .flatMap(dep -> {
                    String consumerId = translateToEndpointId(dep.getFrom());
                    EndpointNode consumerServiceEndpoint = allEndpointNodesMap.get(consumerId);

                    String providerId = translateToEndpointId(dep.getTo());
                    EndpointNode providerServiceEndpoint = allEndpointNodesMap.get(providerId);

                    LOGGER.debug("consumer id: {}, provider id: {}", consumerId, providerId);

                    if (consumerServiceEndpoint != null && providerServiceEndpoint != null) {
                        LOGGER.debug("both get endpoint");
                        CallRelationship callRelationship =
                                new CallRelationship(serviceCallIsTest.getOrDefault(dep.getTo(), false),
                                        consumerServiceEndpoint, providerServiceEndpoint);

                        callRelationshipList.add(callRelationship);
                        return Stream.of(consumerServiceEndpoint);
                    }
                    return Stream.empty();
                });
    }

    private String translateToEndpointId(String id) {
        String serviceName = IDExtractor.getServiceName(id);
        String endpointPath = IDExtractor.getPath(id);
        String endpointHttpMethod = IDExtractor.getHttpMethod(id);
        return EndpointIdFactory.getId(serviceName, endpointPath, endpointHttpMethod);
    }
}
