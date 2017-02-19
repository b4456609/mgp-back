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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class GraphVisualizationFromGraphFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphVisualizationFromGraphFactory.class);


    public GraphDataDTO create(Iterable<EndpointNode> endpointNodes, Iterable<ServiceNode> serviceNodes,
                               List<ServiceWithEndpointPairItem> allServiceWithEndpoint,
                               List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair,
                               List<PathGroup> pathNodeIdGroups, Iterable<ScenarioNode> scenarioNodes,
                               Map<String, Set<String>> errorMarkConsumerAndProvider, Set<String> failedScenario) {
        LOGGER.info("endpoint node {}", endpointNodes);
        LOGGER.info("service node {}", serviceNodes);
        LOGGER.info("all service with endpoint {}", allServiceWithEndpoint);
        LOGGER.info("endpoint node {}", providerEndpointWithConsumerPairPair);
        LOGGER.info("path node group {}", pathNodeIdGroups);
        LOGGER.info("scenario node {}", scenarioNodes);
        LOGGER.info("errorMarkConsumerAndProvider {}", errorMarkConsumerAndProvider);
        LOGGER.info("failedScenario {}", failedScenario);

        long start = System.currentTimeMillis();

        //endpoint node
        List<NodesItem> endpointNodeItems = getEndpointNodeItems(endpointNodes, pathNodeIdGroups);

        //service node
        List<NodesItem> serviceNodeItems = getServiceNodeItems(serviceNodes, pathNodeIdGroups);

        //scenario node
        List<NodesItem> scenarioNodeItems = getScenarioNodeItems(scenarioNodes, failedScenario);

        // all node dto items
        List<NodesItem> allNodes = new ArrayList<>();
        allNodes.addAll(endpointNodeItems);
        allNodes.addAll(serviceNodeItems);
        allNodes.addAll(scenarioNodeItems);

        long node = System.currentTimeMillis();

        // scenario and endpoint pair
        List<ScenarioEndpointPairItem> scenarioEndpointPairItems = getScenarioEndpointPairItem(scenarioNodes);

        long scenario = System.currentTimeMillis();

        //set class name
        for (ServiceWithEndpointPairItem item : allServiceWithEndpoint) {
            item.setClassName(getServiceWithEndpointClassString(pathNodeIdGroups, item.getSource(), item.getTarget()));
        }

        long serviceWithEndpoint = System.currentTimeMillis();

        //set class name
        for (ProviderEndpointWithConsumerPairItem item : providerEndpointWithConsumerPairPair) {
            item.setClassName(getProviderEndpointWithConsumerClass(pathNodeIdGroups, item.getSource(), item.getTarget(),
                    errorMarkConsumerAndProvider));
        }

        long providerEndpointWithConsumer = System.currentTimeMillis();

        LOGGER.info("execution time: node {}ms, scenario {}ms, serviceWithEndpoint {}ms, providerEndpointWithConsumer {}ms",
                node - start, scenario - start, serviceWithEndpoint - start, providerEndpointWithConsumer - start);

        return new GraphDataDTOBuilder()
                .setNodes(allNodes)
                .setScenarioEndpointPair(scenarioEndpointPairItems)
                .setServiceWithEndpointPair(allServiceWithEndpoint)
                .setProviderEndpointWithConsumerPair(providerEndpointWithConsumerPairPair)
                .createGraphDataDTO();
    }

    private List<ScenarioEndpointPairItem> getScenarioEndpointPairItem(Iterable<ScenarioNode> scenarioNodes) {
        return StreamSupport.stream(scenarioNodes.spliterator(),
                true)
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
    }

    private List<NodesItem> getScenarioNodeItems(Iterable<ScenarioNode> scenarioNodes, Set<String> failedScenario) {
        return StreamSupport.stream(scenarioNodes.spliterator(), true)
                .map(scenarioNode -> {
                    String className = "";
                    if (failedScenario != null && failedScenario.contains(scenarioNode.getName())) {
                        className = "error ";
                    }
                    return new NodesItemBuilder().setClassName(className.trim())
                            .setGroup(3)
                            .setId(scenarioNode.getMongoId())
                            .setLabel(scenarioNode.getName())
                            .createNodesItem();
                }).collect(Collectors.toList());
    }

    private List<NodesItem> getServiceNodeItems(Iterable<ServiceNode> serviceNodes, List<PathGroup> pathNodeIdGroups) {
        return StreamSupport.stream(serviceNodes.spliterator(), true)
                .map(serviceNode -> {
                    String className = getClassString(pathNodeIdGroups, serviceNode.getName());
                    return new NodesItemBuilder().setId(serviceNode.getName())
                            .setLabel(LableFactory.createServiceLabel(serviceNode.getName()))
                            .setClassName(className)
                            .setGroup(2)
                            .createNodesItem();
                }).collect(Collectors.toList());
    }

    private List<NodesItem> getEndpointNodeItems(Iterable<EndpointNode> endpointNodes, List<PathGroup> pathNodeIdGroups) {
        return StreamSupport.stream(endpointNodes.spliterator(), true)
                .map(endpointNode -> {
                    String className = getClassString(pathNodeIdGroups, endpointNode.getEndpointId());
                    return new NodesItemBuilder().setId(endpointNode.getEndpointId())
                            .setLabel(LableFactory.createEndpointLabel(endpointNode.getPath(),
                                    endpointNode.getHttpMethod()))
                            .setClassName(className)
                            .setGroup(1)
                            .createNodesItem();
                }).collect(Collectors.toList());
    }

    private String getProviderEndpointWithConsumerClass(List<PathGroup> pathNodeIdGroups,
                                                        String service,
                                                        String endpoint,
                                                        Map<String, Set<String>> errorMarkConsumerAndProvider) {
        StringBuilder className = new StringBuilder();
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            PathGroup group = pathNodeIdGroups.get(i);
            if (group.isServiceCall(service, endpoint)) {
                className.append(String.format("group%d ", i));
                //check is in cyclic group
                if (group.isCyclic()) {
                    className.append(String.format("cyclic%d ", i));
                }
            }
        }

        if (errorMarkConsumerAndProvider != null &&
                errorMarkConsumerAndProvider.get(service) != null &&
                errorMarkConsumerAndProvider.get(service).contains(endpoint)) {
            className.append("error ");
        }
        return className.toString().trim();
    }

    private String getClassString(List<PathGroup> pathNodeIdGroups, String id) {
        StringBuilder className = new StringBuilder();
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            PathGroup group = pathNodeIdGroups.get(i);
            if (group.isContain(id)) {
                if (group.isFirstEndpoint(id)) {
                    className.append(String.format("group%d-start ", i));
                    //check is in cyclic group
                    if (group.isCyclic()) {
                        className.append(String.format("cyclic%d-start ", i));
                    }
                } else {
                    className.append(String.format("group%d ", i));
                    //check is in cyclic group
                    if (group.isCyclic()) {
                        className.append(String.format("cyclic%d ", i));
                    }
                }
            }
        }
        LOGGER.info("finalClassName: {} ", className);
        return className.toString().trim();
    }


    private String getServiceWithEndpointClassString(List<PathGroup> pathNodeIdGroups
            , String id1, String id2) {
        StringBuilder className = new StringBuilder();
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            PathGroup group = pathNodeIdGroups.get(i);
            if (group.isServiceAndEndpoint(id1, id2)) {
                className.append(String.format("group%d ", i));
                //check is in cyclic group
                if (group.isCyclic()) {
                    className.append(String.format("cyclic%d ", i));
                }
            }
        }
        return className.toString().trim();
    }
}
