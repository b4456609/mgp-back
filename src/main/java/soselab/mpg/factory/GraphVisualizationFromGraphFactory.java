package soselab.mpg.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soselab.mpg.dto.graph.*;
import soselab.mpg.model.graph.EndpointNode;
import soselab.mpg.model.graph.ServiceNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class GraphVisualizationFromGraphFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(GraphVisualizationFromGraphFactory.class);


    public GraphVisualization create(Iterable<EndpointNode> endpointNodes, Iterable<ServiceNode> serviceNodes,
                                     List<ServiceWithEndpointPairItem> allServiceWithEndpoint,
                                     List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair,
                                     List<List<String>> pathNodeIdGroups) {
        LOGGER.info(pathNodeIdGroups.toString());

        //endpoint node
        List<NodesItem> endpointNodeItems = StreamSupport.stream(endpointNodes.spliterator(), false)
                .map(endpointNode -> {
                    String className = getClassString(pathNodeIdGroups, endpointNode.getEndpointId());
                    return new NodesItemBuilder().setId(endpointNode.getEndpointId())
                            .setLabel(ServiceLableFactory.createEndpointLabel(endpointNode.getPath(),
                                    endpointNode.getHttpMethod()))
                            .setClassName(className)
                            .setGroup(2)
                            .createNodesItem();
                }).collect(Collectors.toList());

        //service node
        List<NodesItem> serviceNodeItems = StreamSupport.stream(serviceNodes.spliterator(), false)
                .map(serviceNode -> {
                    String className = getClassString(pathNodeIdGroups, serviceNode.getName());
                    return new NodesItemBuilder().setId(serviceNode.getName())
                            .setLabel(ServiceLableFactory.createServiceLabel(serviceNode.getName()))
                            .setClassName(className)
                            .setGroup(1)
                            .createNodesItem();
                }).collect(Collectors.toList());

        // all node dto items
        List<NodesItem> allNodes = new ArrayList<>();
        allNodes.addAll(endpointNodeItems);
        allNodes.addAll(serviceNodeItems);


        //get path group


        //set class name
        for (ServiceWithEndpointPairItem item : allServiceWithEndpoint) {
            item.setClassName(getClassString(pathNodeIdGroups, item.getSource(), item.getTarget()));
        }

        //set class name
        for (ProviderEndpointWithConsumerPairItem item : providerEndpointWithConsumerPairPair) {
            item.setClassName(getClassString(pathNodeIdGroups, item.getSource(), item.getTarget()));
        }

        return new GraphVisualizationBuilder()
                .setNodes(allNodes)
                .setServiceCallEndpointsPair(allServiceWithEndpoint)
                .setProvicerEndpointWithConsumberPair(providerEndpointWithConsumerPairPair)
                .createGraphVisualization();
    }

    private String getClassString(List<List<String>> pathNodeIdGroups, String id) {
        LOGGER.info("getclassstring id:{}", id);
        String className = "";
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            List<String> group = pathNodeIdGroups.get(i);
            LOGGER.info("group: {} contain:{}", group.toString(), group.contains(id));
            if (group.contains(id)) {
                if (group.get(0).equals(id)) {
                    className += String.format("group%d-start ", i);
                } else {
                    className += String.format("group%d ", i);
                }
            }
        }
        LOGGER.info("finalClassName: {} ", className);
        return className;
    }


    private String getClassString(List<List<String>> pathNodeIdGroups, String id1, String id2) {
        String className = "";
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            List<String> group = pathNodeIdGroups.get(i);
            if (group.contains(id1) && group.contains(id2)) {
                className += String.format("group%d ", i);
            }
        }
        return className;
    }
}
