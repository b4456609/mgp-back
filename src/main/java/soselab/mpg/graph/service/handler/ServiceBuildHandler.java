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
    private final ConcurrentHashMap<String, EndpointNode> allEndpointNodesMap;

    @Autowired
    public ServiceBuildHandler(ServiceNodeRepository serviceNodeRepository, EndpointNodeRepository endpointNodeRepository, CallRelationshipRepository callRelationshipRepository, MPDService mpdService) {
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.callRelationshipRepository = callRelationshipRepository;
        this.mpdService = mpdService;
        allEndpointNodesMap = new ConcurrentHashMap<String, EndpointNode>();
    }

    @Override
    public void build() {

        LOGGER.info("build service and endpoint Graph");
        endpointNodeRepository.deleteAll();
        serviceNodeRepository.deleteAll();
        callRelationshipRepository.deleteAll();

        List<MicroserviceProjectDescription> microserviceProjectDescriptions = mpdService
                .getMicroserviceProjectDescriptions();

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(this::createServiceNode)
                .collect(Collectors.toSet());

        // create endpoint service call relationship
        List<CallRelationship> callRelationshipList = microserviceProjectDescriptions.stream()
                .flatMap(this::createEndpointRelation)
                .collect(Collectors.toList());


        //save to database
        serviceNodeRepository.save(serviceNodes);
        callRelationshipRepository.save(callRelationshipList);
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

        // return a new service node
        return new ServiceNode(microserviceProjectDescription.getName(), endpointNodes);
    }

    private Stream<CallRelationship> createEndpointRelation(MicroserviceProjectDescription microserviceProjectDescription) {
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
                        CallRelationship callRelationship = new CallRelationship();
                        callRelationship.setConsumber(consumerServiceEndpoint);
                        callRelationship.setProvider(providerServiceEndpoint);
                        callRelationship.setUnTest(serviceCallIsTest.getOrDefault(dep.getTo(), false));

                        consumerServiceEndpoint.addCallRelationships(callRelationship);
                        return Stream.of(callRelationship);
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
