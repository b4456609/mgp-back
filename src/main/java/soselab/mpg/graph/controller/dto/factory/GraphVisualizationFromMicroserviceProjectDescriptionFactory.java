package soselab.mpg.graph.controller.dto.factory;

import soselab.mpg.graph.controller.dto.*;
import soselab.mpg.mpd.model.Endpoint;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static soselab.mpg.graph.controller.dto.factory.ServiceLableFactory.createEndpointLabel;
import static soselab.mpg.graph.controller.dto.factory.ServiceLableFactory.createServiceLabel;


public class GraphVisualizationFromMicroserviceProjectDescriptionFactory {

    public GraphDataDTO create(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        //node item
        List<NodesItem> nodesItems = getNodesItems(microserviceProjectDescriptions);
        //provider Endpoint With Consumer link
        List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairItems = getCollect(microserviceProjectDescriptions, nodesItems);
        //service and endpoint link
        List<ServiceWithEndpointPairItem> serviceWithEndpointPairItems = getCollect(microserviceProjectDescriptions);

        return new GraphVisualizationBuilder()
                .setNodes(nodesItems)
                .setProvicerEndpointWithConsumberPair(providerEndpointWithConsumerPairItems)
                .setServiceCallEndpointsPair(serviceWithEndpointPairItems)
                .createGraphVisualization();
    }

    private List<ServiceWithEndpointPairItem> getCollect(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        return microserviceProjectDescriptions.stream().flatMap(microserviceProjectDescription -> microserviceProjectDescription.getEndpoint().stream().map(endpoint -> {
            return new ServiceWithEndpointPairItem(endpoint.getId(), "", microserviceProjectDescription.getName());
        })).collect(Collectors.toList());
    }

    private List<ProviderEndpointWithConsumerPairItem> getCollect(List<MicroserviceProjectDescription> microserviceProjectDescriptions,
                                                                  List<NodesItem> nodesItems) {
        return microserviceProjectDescriptions.stream()
                .flatMap(microserviceProjectDescription -> microserviceProjectDescription.getServiceCall().stream()
                        .map(serviceCall -> {
                            return new ProviderEndpointWithConsumerPairItem(microserviceProjectDescription.getName(),
                                    "", ServiceEndpointIdFactory.getId(serviceCall.getProvider(), serviceCall.getPath(), serviceCall.getMethod()));
                        }))
                // remove the service call which has not corresponding endpoint
                // to do report
                .filter(providerEndpointWithConsumerPairItem -> {
                    Optional<NodesItem> any = nodesItems.stream().filter(nodesItem ->
                            providerEndpointWithConsumerPairItem.getTarget().equals(nodesItem.getId())).findAny();
                    return any.isPresent();
                }).collect(Collectors.toList());
    }

    private List<NodesItem> getNodesItems(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
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
                    endpoints.forEach(endpoint ->
                            nodesItemList.add(new NodesItemBuilder()
                                    .setId(endpoint.getId())
                                    .setClassName("")
                                    .setGroup(2)
                                    .setLabel(createEndpointLabel(endpoint.getPath(), endpoint.getMethod()))
                                    .createNodesItem())
                    );
                    return nodesItemList.stream();
                }).collect(Collectors.toList());
    }

}
