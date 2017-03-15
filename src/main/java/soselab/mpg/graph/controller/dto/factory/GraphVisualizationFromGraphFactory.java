package soselab.mpg.graph.controller.dto.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.controller.dto.*;
import soselab.mpg.graph.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class GraphVisualizationFromGraphFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphVisualizationFromGraphFactory.class);
    private ConcurrentHashMap<String, Set<Integer>> idPathIndexsMap;
    private HashMap<String, Set<String>> unTestServiceCallDict = new HashMap<>();

    public GraphDataDTO create(List<EndpointQuery> endpointNodes, List<ServiceQuery> serviceNodes,
                               List<ServiceWithEndpointPairItem> allServiceWithEndpoint,
                               List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair,
                               List<PathGroup> pathNodeIdGroups, Iterable<ScenarioNode> scenarioNodes,
                               List<UnTestServiceCall> unTestServiceCall,
                               Map<String, Set<String>> errorMarkConsumerAndProvider, Set<String> failedScenario) {
        LOGGER.debug("endpoint node {}", endpointNodes);
        LOGGER.debug("service node {}", serviceNodes);
        LOGGER.debug("all service with endpoint {}", allServiceWithEndpoint);
        LOGGER.debug("providerEndpointWithConsumerPairPair {}", providerEndpointWithConsumerPairPair);
        LOGGER.debug("path node group {}", pathNodeIdGroups);
        LOGGER.debug("scenario node {}", scenarioNodes);
        LOGGER.debug("unTestServiceCall {}", unTestServiceCall);
        LOGGER.debug("errorMarkConsumerAndProvider {}", errorMarkConsumerAndProvider);
        LOGGER.debug("failedScenario {}", failedScenario);

        long start = System.currentTimeMillis();

        for (UnTestServiceCall testServiceCall : unTestServiceCall) {
            if (unTestServiceCallDict.containsKey(testServiceCall.getFrom())) {
                unTestServiceCallDict.get(testServiceCall.getFrom())
                        .add(testServiceCall.getTo());
            } else {
                HashSet<String> enpointSet = new HashSet<>();
                enpointSet.add(testServiceCall.getTo());
                unTestServiceCallDict.put(testServiceCall.getFrom(), enpointSet);
            }
        }

        idPathIndexsMap = new ConcurrentHashMap<>();
        for (int i = 0; i < pathNodeIdGroups.size(); i++) {
            final int index = i;
            PathGroup pathGroup = pathNodeIdGroups.get(i);
            Stream<String> serviceStream = pathGroup.getServices().stream();
            Stream<String> endpointIdStream = pathGroup.getPaths().stream()
                    .flatMap(Collection::stream)
                    .distinct();
            Stream.concat(serviceStream, endpointIdStream)
                    .forEach(id -> {
                        if (idPathIndexsMap.containsKey(id)) {
                            idPathIndexsMap.get(id).add(index);
                        } else {
                            Set<Integer> indexs = new HashSet<>();
                            indexs.add(index);
                            idPathIndexsMap.put(id, indexs);
                        }
                    });
        }

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
    }

    private List<NodesItem> getScenarioNodeItems(Iterable<ScenarioNode> scenarioNodes, Set<String> failedScenario) {
        return StreamSupport.stream(scenarioNodes.spliterator(), false)
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

    private List<NodesItem> getServiceNodeItems(List<ServiceQuery> serviceNodes, List<PathGroup> pathNodeIdGroups) {
        return StreamSupport.stream(serviceNodes.spliterator(), false)
                .map(serviceNode -> {
                    String className = getClassString(pathNodeIdGroups, serviceNode.getName());
                    return new NodesItemBuilder().setId(serviceNode.getName())
                            .setLabel(LableFactory.createServiceLabel(serviceNode.getName()))
                            .setClassName(className)
                            .setGroup(2)
                            .createNodesItem();
                }).collect(Collectors.toList());
    }

    private List<NodesItem> getEndpointNodeItems(List<EndpointQuery> endpointNodes, List<PathGroup> pathNodeIdGroups) {
        return StreamSupport.stream(endpointNodes.spliterator(), false)
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
        Set<String> endpointIdSets = unTestServiceCallDict.get(service);
        if (endpointIdSets != null && endpointIdSets.contains(endpoint)) {
            className.append("untest ");
        }
        if (idPathIndexsMap.containsKey(service) && idPathIndexsMap.containsKey(endpoint)) {
            Set<Integer> index1 = idPathIndexsMap.get(service);
            Set<Integer> index2 = idPathIndexsMap.get(endpoint);
            Set<Integer> intersection = new HashSet<Integer>(index1); // use the copy constructor
            intersection.retainAll(index2);
            intersection.forEach(index -> {
                PathGroup pathGroup = pathNodeIdGroups.get(index);
                if (pathGroup.isServiceCall(service, endpoint)) {
                    className.append(String.format("group%d ", index));
                    className.append(getCyclicClassString(index, pathGroup, false));
                }
            });
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
        if (idPathIndexsMap.containsKey(id)) {
            Set<Integer> indexs = idPathIndexsMap.get(id);
            indexs.forEach(index -> {
                PathGroup group = pathNodeIdGroups.get(index);
                if (group.isFirstEndpoint(id)) {
                    className.append(String.format("group%d-start ", index));
                    className.append(getCyclicClassString(index, group, true));
                } else {
                    className.append(String.format("group%d ", index));
                    className.append(getCyclicClassString(index, group, false));
                }
            });
        }
        LOGGER.debug("finalClassName: {} ", className);
        return className.toString().trim();
    }


    private String getServiceWithEndpointClassString(List<PathGroup> pathNodeIdGroups
            , String id1, String id2) {
        StringBuilder className = new StringBuilder();
        if (idPathIndexsMap.containsKey(id1) && idPathIndexsMap.containsKey(id2)) {
            Set<Integer> index1 = idPathIndexsMap.get(id1);
            Set<Integer> index2 = idPathIndexsMap.get(id2);
            Set<Integer> intersection = new HashSet<Integer>(index1); // use the copy constructor
            intersection.retainAll(index2);
            intersection.forEach(index -> {
                PathGroup pathGroup = pathNodeIdGroups.get(index);
                if (pathGroup.isServiceAndEndpoint(id1, id2)) {
                    className.append(String.format("group%d ", index));
                    className.append(getCyclicClassString(index, pathGroup, false));
                }
            });
        }
        return className.toString().trim();
    }


    private String getCyclicClassString(Integer index, PathGroup pathGroup, boolean isStartNode) {
        String postfix = "";
        if (isStartNode)
            postfix = "-start";

        if (pathGroup.getCyclicType() == CyclicType.WEEK) {
            return String.format("cyclic%d%s ", index, postfix);
        } else if (pathGroup.getCyclicType() == CyclicType.STRONG) {
            return String.format("cyclic-enhance%d%s ", index, postfix);
        }
        return "";
    }

}
