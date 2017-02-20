package soselab.mpg.graph.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.controller.dto.factory.EndpointIdFactory;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.model.Endpoint2ServiceCallDependency;
import soselab.mpg.mpd.model.IDExtractor;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.service.MPDService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ServiceBuildHandler implements GraphBuildHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBuildHandler.class);

    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;
    private final MPDService mpdService;
    private final ConcurrentHashMap<String, EndpointNode> allEndpointNodesMap;

    @Autowired
    public ServiceBuildHandler(ServiceNodeRepository serviceNodeRepository, EndpointNodeRepository endpointNodeRepository, MPDService mpdService) {
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.mpdService = mpdService;
        allEndpointNodesMap = new ConcurrentHashMap<String, EndpointNode>();
    }

    @Override
    public void build() {

        LOGGER.info("build service and endpoint Graph");
        endpointNodeRepository.deleteAll();
        serviceNodeRepository.deleteAll();

        List<MicroserviceProjectDescription> microserviceProjectDescriptions = mpdService
                .getMicroserviceProjectDescriptions();

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(this::createServiceNode)
                .collect(Collectors.toSet());

        // create endpoint service call relationship
        microserviceProjectDescriptions
                .stream()
                .forEach(this::createEndpointRelation);


        //save to database
        serviceNodeRepository.save(serviceNodes);
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

    private void createEndpointRelation(MicroserviceProjectDescription microserviceProjectDescription) {
        List<Endpoint2ServiceCallDependency> endpoint2ServiceCallDependency = microserviceProjectDescription.getEndpointDep();
        endpoint2ServiceCallDependency.forEach(dep -> {
            String consumerId = translateToEndpointId(dep.getFrom());
            EndpointNode consumerServiceEndpoint = allEndpointNodesMap.get(consumerId);

            String providerId = translateToEndpointId(dep.getTo());
            EndpointNode providerServiceEndpoint = allEndpointNodesMap.get(providerId);

            if (consumerServiceEndpoint != null && providerServiceEndpoint != null) {
                consumerServiceEndpoint.addServiceCallEndpoint(providerServiceEndpoint);
            }
        });
    }

    private String translateToEndpointId(String id) {
        String serviceName = IDExtractor.getServiceName(id);
        String endpointPath = IDExtractor.getPath(id);
        String endpointHttpMethod = IDExtractor.getHttpMethod(id);
        return EndpointIdFactory.getId(serviceName, endpointPath, endpointHttpMethod);
    }
}
