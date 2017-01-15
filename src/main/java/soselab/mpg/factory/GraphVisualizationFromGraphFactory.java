package soselab.mpg.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.dto.graph.*;
import soselab.mpg.model.graph.EndpointNode;
import soselab.mpg.model.graph.ServiceNode;
import soselab.mpg.repository.neo4j.EndpointNodeRepository;
import soselab.mpg.repository.neo4j.ServiceNodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class GraphVisualizationFromGraphFactory {

    @Autowired
    ServiceNodeRepository serviceNodeRepository;

    @Autowired
    EndpointNodeRepository endpointNodeRepository;

    public GraphVisualization create() {
        //endpoint node
        Iterable<EndpointNode> endpointNodes = endpointNodeRepository.findAll();
        List<NodesItem> endpointNodeItems = StreamSupport.stream(endpointNodes.spliterator(), false)
                .map(endpointNode -> {
                    return new NodesItemBuilder().setId(endpointNode.getEndpointId())
                            .setLabel(ServiceLableFactory.createEndpointLabel(endpointNode.getPath(), endpointNode.getHttpMethod()))
                            .setClassName("")
                            .setGroup(2)
                            .createNodesItem();
                }).collect(Collectors.toList());

        //service node
        Iterable<ServiceNode> serviceNodes = serviceNodeRepository.findAll();
        List<NodesItem> serviceNodeItems = StreamSupport.stream(serviceNodes.spliterator(), false)
                .map(serviceNode -> {
                    return new NodesItemBuilder().setId(serviceNode.getName())
                            .setLabel(ServiceLableFactory.createServiceLabel(serviceNode.getName()))
                            .setClassName("")
                            .setGroup(1)
                            .createNodesItem();
                }).collect(Collectors.toList());

        // all node dto items
        List<NodesItem> allNodes = new ArrayList<>();
        allNodes.addAll(endpointNodeItems);
        allNodes.addAll(serviceNodeItems);

        //set class name
        List<ServiceWithEndpointPairItem> allServiceWithEndpoint = serviceNodeRepository
                .getAllServiceWithEndpoint();

        for (ServiceWithEndpointPairItem item : allServiceWithEndpoint) {
            item.setClassName("");
        }

        //set class name
        List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair = endpointNodeRepository
                .getProviderEndpointWithConsumerPairPair();

        for (ProviderEndpointWithConsumerPairItem providerEndpointWithConsumerPairItem : providerEndpointWithConsumerPairPair) {
            providerEndpointWithConsumerPairItem.setClassName("");
        }

        return new GraphVisualizationBuilder()
                .setNodes(allNodes)
                .setServiceCallEndpointsPair(allServiceWithEndpoint)
                .setProvicerEndpointWithConsumberPair(providerEndpointWithConsumerPairPair)
                .createGraphVisualization();
    }
}
