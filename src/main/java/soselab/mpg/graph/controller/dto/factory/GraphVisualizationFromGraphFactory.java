package soselab.mpg.graph.controller.dto.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.controller.dto.*;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.model.ScenarioNode;
import soselab.mpg.graph.model.ServiceNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class GraphVisualizationFromGraphFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphVisualizationFromGraphFactory.class);


    public GraphDataDTO create(Iterable<EndpointNode> endpointNodes, Iterable<ServiceNode> serviceNodes,
                               List<ServiceWithEndpointPairItem> allServiceWithEndpoint,
                               List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair,
                               List<PathGroup> pathNodeIdGroups, Iterable<ScenarioNode> scenarioNodes) {
        LOGGER.info("endpoint node {}", endpointNodes);
        LOGGER.info("service node {}", serviceNodes);
        LOGGER.info("all service with endpoint {}", allServiceWithEndpoint);
        LOGGER.info("endpoint node {}", providerEndpointWithConsumerPairPair);
        LOGGER.info("path node group {}", pathNodeIdGroups);
        LOGGER.info("scenario node {}", scenarioNodes);

        //endpoint node
        List<NodesItem> endpointNodeItems = StreamSupport.stream(endpointNodes.spliterator(), false)
                .map(endpointNode -> {
                    String className = getClassString(pathNodeIdGroups, endpointNode.getEndpointId());
                    return new NodesItemBuilder().setId(endpointNode.getEndpointId())
                            .setLabel(LableFactory.createEndpointLabel(endpointNode.getPath(),
                                    endpointNode.getHttpMethod()))
                            .setClassName(className)
                            .setGroup(1)
                            .createNodesItem();
                }).collect(Collectors.toList());

        //service node
        List<NodesItem> serviceNodeItems = StreamSupport.stream(serviceNodes.spliterator(), false)
                .map(serviceNode -> {
                    String className = getClassString(pathNodeIdGroups, serviceNode.getName());
                    return new NodesItemBuilder().setId(serviceNode.getName())
                            .setLabel(LableFactory.createServiceLabel(serviceNode.getName()))
                            .setClassName(className)
                            .setGroup(2)
                            .createNodesItem();
                }).collect(Collectors.toList());

        //scenario node
        List<NodesItem> scenarioNodeItems = StreamSupport.stream(scenarioNodes.spliterator(), false)
                .map(scenarioNode -> {
                    String className = "";
                    return new NodesItemBuilder().setClassName(className)
                            .setGroup(3)
                            .setId(scenarioNode.getMongoId())
                            .setLabel(scenarioNode.getName())
                            .createNodesItem();
                }).collect(Collectors.toList());

        // all node dto items
        List<NodesItem> allNodes = new ArrayList<>();
        allNodes.addAll(endpointNodeItems);
        allNodes.addAll(serviceNodeItems);
        allNodes.addAll(scenarioNodeItems);

        // scenario and endpoint pair
        List<ScenarioEndpointPairItem> scenarioEndpointPairItems = StreamSupport.stream(scenarioNodes.spliterator(),
                false)
                .flatMap(scenarioNode -> {
                    if (scenarioNode.getEndpointNodes() == null)
                        return Stream.empty();
                    return scenarioNode.getEndpointNodes().stream()
                            .map(endpointNode -> {
                                String className = "";
                                return new ScenarioEndpointPairItem(scenarioNode.getMongoId(),
                                        endpointNode.getEndpointId(), className);
                            });
                }).collect(Collectors.toList());


        //set class name
        for (ServiceWithEndpointPairItem item : allServiceWithEndpoint) {
            item.setClassName(getServiceWithEndpointClassString(pathNodeIdGroups, item.getSource(), item.getTarget()));
        }

        //set class name
        for (ProviderEndpointWithConsumerPairItem item : providerEndpointWithConsumerPairPair) {
            item.setClassName(getProviderEndpointWithConsumerClass(pathNodeIdGroups, item.getSource(), item.getTarget()));
        }

        return new GraphDataDTOBuilder()
                .setNodes(allNodes)
                .setScenarioEndpointPair(scenarioEndpointPairItems)
                .setServiceWithEndpointPair(allServiceWithEndpoint)
                .setProviderEndpointWithConsumerPair(providerEndpointWithConsumerPairPair)
                .createGraphDataDTO();
    }

    private String getProviderEndpointWithConsumerClass(List<PathGroup> pathNodeIdGroups, String service, String endpoint) {
        String className = "";
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            PathGroup group = pathNodeIdGroups.get(i);
            if (group.isServiceCall(service, endpoint)) {
                className += String.format("group%d ", i);
                //check is in cyclic group
                if (group.isCyclic()) {
                    className += String.format("cyclic%d ", i);
                }
            }
        }
        return className;
    }

    private String getClassString(List<PathGroup> pathNodeIdGroups, String id) {
        LOGGER.info("getclassstring id:{}", id);
        String className = "";
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            PathGroup group = pathNodeIdGroups.get(i);
            LOGGER.info("group: {} contain:{}", group.toString(), group.isContain(id));
            if (group.isContain(id)) {
                if (group.isFirstEndpoint(id)) {
                    className += String.format("group%d-start ", i);
                    //check is in cyclic group
                    if (group.isCyclic()) {
                        className += String.format("cyclic%d-start ", i);
                    }
                } else {
                    className += String.format("group%d ", i);
                    //check is in cyclic group
                    if (group.isCyclic()) {
                        className += String.format("cyclic%d ", i);
                    }
                }
            }
        }
        LOGGER.info("finalClassName: {} ", className);
        return className;
    }


    private String getServiceWithEndpointClassString(List<PathGroup> pathNodeIdGroups
            , String id1, String id2) {
        String className = "";
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            PathGroup group = pathNodeIdGroups.get(i);
            if (group.isServiceAndEndpoint(id1, id2)) {
                className += String.format("group%d ", i);
                //check is in cyclic group
                if (group.isCyclic()) {
                    className += String.format("cyclic%d ", i);
                }
            }
        }
        return className;
    }
}
