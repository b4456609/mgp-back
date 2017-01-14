package soselab.mpg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.factory.GraphVisualizationFactory;
import soselab.mpg.model.ServiceName;
import soselab.mpg.model.graph.EndpointNode;
import soselab.mpg.model.graph.ServiceNode;
import soselab.mpg.model.mpd.Endpoint2ServiceCallDependency;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphService.class);

    @Autowired
    GraphVisualizationFactory graphVisualizationFactory;
    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;
    @Autowired
    private ServiceNameRepository serviceNameRepository;

    public GraphVisualization getVisualizationData() {
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = getMicroserviceProjectDescriptions();

        return graphVisualizationFactory.create(microserviceProjectDescriptions);
    }

    private List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions() {
        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        LOGGER.info("all service names {}",serviceNames.toString());

        return serviceNames.stream().map(serviceName -> {
            return microserviceProjectDescriptionRepository
                    .findFirstByNameOrderByTimestampAsc(serviceName.getServiceName());
        }).collect(Collectors.toList());
    }

    public void constructGrapht() {
        //get all latest service project description
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = getMicroserviceProjectDescriptions();

        //all endpoint node
        Set<EndpointNode> allEndpointNodes = new HashSet<>();

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(microserviceProjectDescription -> {
                    // generate service's endpoint nodes
                    Set<EndpointNode> endpointNodes = microserviceProjectDescription.getEndpoint().stream()
                            .map(endpoint -> {
                                EndpointNode endpointNode = new EndpointNode(endpoint.getPath(), endpoint.getMethod());
                                //add to all endpoint for call realtionship
                                allEndpointNodes.add(endpointNode);
                                return endpointNode;
                            }).collect(Collectors.toSet());
                    return new ServiceNode(microserviceProjectDescription.getName(), endpointNodes);
                }).collect(Collectors.toSet());

        microserviceProjectDescriptions.stream()
                .map(microserviceProjectDescription -> {
                    List<Endpoint2ServiceCallDependency> endpoint2ServiceCallDependency = microserviceProjectDescription.getEndpointDep();
                    for (Endpoint2ServiceCallDependency dep : endpoint2ServiceCallDependency) {
                        // extract info from endpoint id
                        String[] endpointSplit = dep.getFrom().split(" ");
                        String serviceName = endpointSplit[0];
                        String endpointPath = endpointSplit[2];
                        String endpointHttpMethod = endpointSplit[3];

                        // extract info from service id
                        String[] serviceCallSplit = dep.getFrom().split(" ");
                        String serviceCallname = serviceCallSplit[0];
                        String serviceCallpath = serviceCallSplit[2];
                        String serviceCallhttpMethod = serviceCallSplit[3];

                        //find corresponding endpoint from service endpoints
                        EndpointNode endpointNode1 = serviceNodes.stream().filter(serviceNode -> {
                            return serviceNode.getName().equals(serviceCallname);
                        }).map(serviceNode -> {
                            return serviceNode.getEndpointNodes().stream().filter(
                                    endpointNode -> {
                                        return endpointNode.getPath().equals(serviceCallpath) &&
                                                endpointNode.getHttpMethod().equals(serviceCallhttpMethod);
                                    }
                            ).findAny().orElseThrow(NoSuchEndpointException::new);
                        }).findAny().orElseThrow(NoSuchEndpointException::new);

                        // if match add to servicenode
                        ServiceNode serviceNode1 = serviceNodes.stream().filter(serviceNode -> {
                            return serviceNode.getName().equals(serviceName);
                        }).findAny().get();

                    }
                });
    }
}
