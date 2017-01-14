package soselab.mpg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.factory.GraphVisualizationFromGraphFactory;
import soselab.mpg.factory.ServiceEndpointIdFactory;
import soselab.mpg.model.ServiceName;
import soselab.mpg.model.graph.EndpointNode;
import soselab.mpg.model.graph.ServiceNode;
import soselab.mpg.model.mpd.Endpoint2ServiceCallDependency;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;
import soselab.mpg.repository.neo4j.EndpointNodeRepository;
import soselab.mpg.repository.neo4j.ServiceNodeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphService.class);

//    @Autowired
//    GraphVisualizationFactory graphVisualizationFactory;

    @Autowired
    GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory;

    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Autowired
    private ServiceNameRepository serviceNameRepository;

    @Autowired
    private ServiceNodeRepository serviceNodeRepository;

    @Autowired
    private EndpointNodeRepository endpointNodeRepository;

    public GraphVisualization getVisualizationData() {
//        List<MicroserviceProjectDescription> microserviceProjectDescriptions = getMicroserviceProjectDescriptions();
//
//        return graphVisualizationFactory.create(microserviceProjectDescriptions);
        return graphVisualizationFromGraphFactory.create();
    }

    private List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions() {
        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        LOGGER.info("all service names {}",serviceNames.toString());

        return serviceNames.stream().map(serviceName -> {
            return microserviceProjectDescriptionRepository
                    .findFirstByNameOrderByTimestampAsc(serviceName.getServiceName());
        }).collect(Collectors.toList());
    }

    public void buildGraphFromLatestMicroserviceProjectDescription() {
        //get all latest service project description
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = getMicroserviceProjectDescriptions();

        //all endpoint node
        Set<EndpointNode> allEndpointNodes = new HashSet<>();

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(microserviceProjectDescription -> {
                    return createServiceNode(allEndpointNodes, microserviceProjectDescription);
                }).collect(Collectors.toSet());

        //save to database
        serviceNodeRepository.save(serviceNodes);

        // create endpoint srevice call relationship
        microserviceProjectDescriptions
                .forEach(this::createEndpointRelation);
    }

    private ServiceNode createServiceNode(Set<EndpointNode> allEndpointNodes
            , MicroserviceProjectDescription microserviceProjectDescription) {
        // generate service's endpoint nodes
        Set<EndpointNode> endpointNodes = microserviceProjectDescription.getEndpoint().stream()
                .map(endpoint -> {
                    String path = endpoint.getPath();
                    String method = endpoint.getMethod();
                    String name = microserviceProjectDescription.getName();
                    String id = ServiceEndpointIdFactory.getId(name, path, method);
                    EndpointNode endpointNode = new EndpointNode(id, path, method);

                    //add to all endpoint for call realtionship
                    allEndpointNodes.add(endpointNode);
                    return endpointNode;
                }).collect(Collectors.toSet());

        // return a new service node
        return new ServiceNode(microserviceProjectDescription.getName(), endpointNodes);
    }

    private void createEndpointRelation(MicroserviceProjectDescription microserviceProjectDescription) {
        List<Endpoint2ServiceCallDependency> endpoint2ServiceCallDependency = microserviceProjectDescription.getEndpointDep();
        for (Endpoint2ServiceCallDependency dep : endpoint2ServiceCallDependency) {
            // extract info from endpoint id
            String[] endpointSplit = dep.getFrom().split(" ");
            String serviceName = endpointSplit[0];
            String endpointPath = endpointSplit[2];
            String endpointHttpMethod = endpointSplit[3];

            // extract info from service id
            String[] serviceCallSplit = dep.getTo().split(" ");
            String serviceCallname = serviceCallSplit[0];
            String serviceCallpath = serviceCallSplit[2];
            String serviceCallhttpMethod = serviceCallSplit[3];

            // get from database and build relationship
            EndpointNode consumerServiceEndpoint = endpointNodeRepository
                    .findServiceEndpointByPathAndHttpMethod(serviceName, endpointHttpMethod, endpointPath);

            EndpointNode providerServiceEndpoint = endpointNodeRepository
                    .findServiceEndpointByPathAndHttpMethod(serviceCallname, serviceCallhttpMethod, serviceCallpath);

            if (consumerServiceEndpoint != null && providerServiceEndpoint != null) {
                consumerServiceEndpoint.addServiceCallEndpoint(providerServiceEndpoint);
            }
            endpointNodeRepository.save(consumerServiceEndpoint);
        }
    }
}
