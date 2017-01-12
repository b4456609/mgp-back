package soselab.mpg.factory;

import org.springframework.stereotype.Component;
import soselab.mpg.dto.graph.*;
import soselab.mpg.model.mpd.Endpoint;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static soselab.mpg.factory.ServiceLableFactory.createEndpointLabel;
import static soselab.mpg.factory.ServiceLableFactory.createServiceLabel;

@Component
public class GraphVisualizationFactory {
    public static GraphVisualization create(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        List<NodesItem> nodesItems = microserviceProjectDescriptions.stream()
                .flatMap(microserviceProjectDescription -> {

                    //endpoint node
                    List<Endpoint> endpoints = microserviceProjectDescription.getEndpoint();

                    //service node
                    String serviceId = microserviceProjectDescription.getId();
                    String serviceName = microserviceProjectDescription.getName();


                    List<NodesItem> nodesItemList = new ArrayList<>();

                    //add to node item
                    nodesItemList.add(new NodesItemBuilder()
                            .setId(serviceName)
                            .setLabel(createServiceLabel(serviceName))
                            .setClassName("")
                            .setGroup(1)
                            .createNodesItem());

                    //add endpoint nodes
                    endpoints.stream().forEach(endpoint -> {
                        nodesItemList.add(new NodesItemBuilder()
                                .setId(endpoint.getId())
                                .setClassName("")
                                .setGroup(2)
                                .setLabel(createEndpointLabel(endpoint.getPath(), endpoint.getMethod()))
                                .createNodesItem());
                    });
                    return nodesItemList.stream();
                }).collect(Collectors.toList());

        List<ServiceCallItem> serviceCallItems = microserviceProjectDescriptions.stream().flatMap(microserviceProjectDescription -> {
            return microserviceProjectDescription.getServiceCall().stream()
                    .map(serviceCall -> {
                        return new ServiceCallItem(microserviceProjectDescription.getName(), "", serviceCall.getId());
                    });
        }).collect(Collectors.toList());

        List<ServiceCallEndpointsPairItem> serviceCallEndpointsPairItems = microserviceProjectDescriptions.stream().flatMap(microserviceProjectDescription -> {
            return microserviceProjectDescription.getEndpointDep().stream().map(endpointDep -> {
                return new ServiceCallEndpointsPairItem(endpointDep.getFrom(), "", endpointDep.getTo());
            });
        }).collect(Collectors.toList());

        return new GraphVisualizationBuilder()
                .setNodes(nodesItems)
                .setServiceCall(serviceCallItems)
                .setServiceCallEndpointsPair(serviceCallEndpointsPairItems)
                .createGraphVisualization();
    }

}
