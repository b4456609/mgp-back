package soselab.mpg.factory;

import org.springframework.stereotype.Component;
import soselab.mpg.dto.graph.*;
import soselab.mpg.model.mpd.Endpoint;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static soselab.mpg.factory.ServiceLableFactory.createEndpointLabel;
import static soselab.mpg.factory.ServiceLableFactory.createServiceLabel;

@Component
public class GraphVisualizationFactory {
    public static GraphVisualization create(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        //node item
        List<NodesItem> nodesItems = getNodesItems(microserviceProjectDescriptions);
        //provider Endpoint With Consumer link
        List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairItems = getCollect(microserviceProjectDescriptions, nodesItems);
        //service and endpoint link
        List<ServiceWithEndpointPairItem> serviceWithEndpointPairItems = getCollect(microserviceProjectDescriptions);

        return new GraphVisualizationBuilder()
                .setNodes(nodesItems)
                .setServiceCall(providerEndpointWithConsumerPairItems)
                .setServiceCallEndpointsPair(serviceWithEndpointPairItems)
                .createGraphVisualization();
    }

    private static List<ServiceWithEndpointPairItem> getCollect(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        return microserviceProjectDescriptions.stream().flatMap(microserviceProjectDescription -> {
            return microserviceProjectDescription.getEndpoint().stream().map(endpoint -> {
                return new ServiceWithEndpointPairItem(endpoint.getId(), "", microserviceProjectDescription.getName());
            });
        }).collect(Collectors.toList());
    }

    private static List<ProviderEndpointWithConsumerPairItem> getCollect(List<MicroserviceProjectDescription> microserviceProjectDescriptions, List<NodesItem> nodesItems) {
        return microserviceProjectDescriptions.stream()
                .flatMap(microserviceProjectDescription -> {
                    return microserviceProjectDescription.getServiceCall().stream()
                            .map(serviceCall -> {
                                return new ProviderEndpointWithConsumerPairItem(microserviceProjectDescription.getName(), "", ServiceEndpointIdFactory.getId(serviceCall.getProvider(), serviceCall.getPath(), serviceCall.getMethod()));
                            });
                })
                // remove the service call which has not corresponding endpoint
                // to do report
                .filter(providerEndpointWithConsumerPairItem -> {
                    Optional<NodesItem> any = nodesItems.stream().filter(nodesItem -> {
                        return providerEndpointWithConsumerPairItem.getTarget().equals(nodesItem.getId());
                    }).findAny();
                    return any.isPresent();
                }).collect(Collectors.toList());
    }

    private static List<NodesItem> getNodesItems(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        return microserviceProjectDescriptions.stream()
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
    }

}
