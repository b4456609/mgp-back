package soselab.mpg.graph.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.controller.dto.factory.ServiceEndpointIdFactory;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.model.Endpoint2ServiceCallDependency;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.service.MPDService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@Component
public class ServiceBuildHandler implements GraphBuildHandler {

    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;
    private final MPDService mpdService;

    @Autowired
    public ServiceBuildHandler(ServiceNodeRepository serviceNodeRepository, EndpointNodeRepository endpointNodeRepository, MPDService mpdService) {
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.mpdService = mpdService;
    }

    @Override
    public void build() {

        LOGGER.info("build Graph");
        //get all latest service project description
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();

        //all endpoint node
        Set<EndpointNode> allEndpointNodes = new HashSet<EndpointNode>();

        List<MicroserviceProjectDescription> microserviceProjectDescriptions = mpdService.getMicroserviceProjectDescriptions();

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(microserviceProjectDescription -> createServiceNode(allEndpointNodes, microserviceProjectDescription))
                .collect(Collectors.toSet());

        //save to database
        serviceNodeRepository.save(serviceNodes);

        // create endpoint service call relationship
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
