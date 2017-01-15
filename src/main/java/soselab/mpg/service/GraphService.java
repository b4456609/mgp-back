package soselab.mpg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.dto.graph.ProviderEndpointWithConsumerPairItem;
import soselab.mpg.dto.graph.ServiceWithEndpointPairItem;
import soselab.mpg.factory.GraphVisualizationFromGraphFactory;
import soselab.mpg.factory.ServiceEndpointIdFactory;
import soselab.mpg.model.ServiceName;
import soselab.mpg.model.graph.EndpointNode;
import soselab.mpg.model.graph.ServiceNode;
import soselab.mpg.model.mpd.Endpoint2ServiceCallDependency;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;
import soselab.mpg.repository.neo4j.EndpointNodeRepository;
import soselab.mpg.repository.neo4j.ServiceNodeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphService.class);

//    @Autowired
//    GraphVisualizationFactory graphVisualizationFactory;

    @Autowired
    GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory;

    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Autowired
    private ServiceNameRepository serviceNameRepository;

    @Autowired
    private ServiceNodeRepository serviceNodeRepository;

    @Autowired
    private EndpointNodeRepository endpointNodeRepository;

    public GraphVisualization getVisualizationData() {
        //endpoint node
        Iterable<EndpointNode> endpointNodes = endpointNodeRepository.findAll();

        //service node
        Iterable<ServiceNode> serviceNodes = serviceNodeRepository.findAll();

        //Service and Endpoint relationship
        List<ServiceWithEndpointPairItem> allServiceWithEndpoint = serviceNodeRepository
                .getAllServiceWithEndpoint();

        //ProviderEndpoint Consumer relationship
        List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPairPair = endpointNodeRepository
                .getProviderEndpointWithConsumerPairPair();

        List<List<String>> pathNodeIdGroups = getPathNodeIdGroups();

        return graphVisualizationFromGraphFactory.create(endpointNodes, serviceNodes, allServiceWithEndpoint, providerEndpointWithConsumerPairPair, pathNodeIdGroups);
    }

    public void buildGraphFromLatestMicroserviceProjectDescription() {
        //get all latest service project description
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = getMicroserviceProjectDescriptions();

        //all endpoint node
        Set<EndpointNode> allEndpointNodes = new HashSet<>();

        //get all serviceNode
        Set<ServiceNode> serviceNodes = microserviceProjectDescriptions.stream()
                .map(microserviceProjectDescription -> {
                    return createServiceNode(allEndpointNodes, microserviceProjectDescription);
                }).collect(Collectors.toSet());

        //save to database
        serviceNodeRepository.save(serviceNodes);

        // create endpoint srevice call relationship
        microserviceProjectDescriptions
                .forEach(this::createEndpointRelation);
    }

    public List<List<String>> getPathNodeIdGroups() {
        List<List<LinkedHashMap>> pathEndpoints = endpointNodeRepository.getPathEndpoints();

        //translation to endpoint id string list
        List<List<String>> endpointIds = pathEndpoints.stream()
                .map(linkedHashMaps -> {
                    return linkedHashMaps.stream().map(linkedHashMap -> {
                        return (String) linkedHashMap.get("endpointId");
                    }).collect(Collectors.toList());
                })
                .collect(Collectors.toList());


        List<List<String>> removeSet = getremoveDuplicateShorterPath(endpointIds);
        endpointIds.removeAll(removeSet);

        //group set from different set
        List<List<String>> getGroupSet = getPathGroup(endpointIds);
        List<List<String>> serviceAndEndpointGroupSet = getServiceWithEndpointsGroup(getGroupSet);

        return serviceAndEndpointGroupSet;
    }

    private List<List<String>> getServiceWithEndpointsGroup(List<List<String>> groups) {
        return groups.stream().map(group -> {
            Set<String> collect = group.stream().map(item -> {
                return serviceNodeRepository.getServiceNameByEndpoint(item);
            }).collect(Collectors.toSet());
            group.addAll(collect);
            return group;
        }).collect(Collectors.toList());
    }

    private List<List<String>> getPathGroup(List<List<String>> endpointIds) {
        int endpointIdsSize = endpointIds.size();

        Map<Integer, Set<Integer>> indexList2Group = new HashMap<>();
        for (int i = 0; i < endpointIdsSize; i++) {
            indexList2Group.put(i, null);
        }

        for (int i = 0; i < endpointIdsSize; i++) {
            for (int j = i + 1; j < endpointIdsSize; j++) {
                //compare first id list if the same, they are same group
                if (endpointIds.get(i).get(0).equals(endpointIds.get(j).get(0))) {
                    Set<Integer> iGroup = indexList2Group.get(i);
                    Set<Integer> jGroup = indexList2Group.get(j);
                    if (iGroup != null && jGroup == null) {
                        iGroup.add(j);
                        indexList2Group.put(j, iGroup);
                    } else if (iGroup == null && jGroup != null) {
                        jGroup.add(j);
                        indexList2Group.put(i, jGroup);
                    } else if (iGroup != null && jGroup != null) {
                        jGroup.addAll(iGroup);
                        indexList2Group.put(i, jGroup);
                    } else if (iGroup == null && jGroup == null) {
                        Set<Integer> newGroup = new HashSet<>();
                        newGroup.add(i);
                        newGroup.add(j);

                        indexList2Group.put(i, newGroup);
                        indexList2Group.put(j, newGroup);
                    }
                }
            }
        }

        Map<Integer, Boolean> isGroupCheckList = new HashMap<>();
        for (int i = 0; i < endpointIdsSize; i++) {
            isGroupCheckList.put(i, false);
        }

        List<List<String>> newListGroup = new ArrayList<>();
        for (Integer index : indexList2Group.keySet()) {
            //index of group
            Set<Integer> indexGroup = indexList2Group.get(index);

            //if the the index not a group or it has already in other group
            if (indexGroup == null || isGroupCheckList.get(index)) {
                continue;
            }


            //use character of the set to remove duplicate node in the list
            Set<String> tempGroup = new HashSet<>();

            boolean isFirstTime = true;
            String startNode = null;
            for (Integer groupIndex : indexGroup) {
                //get the start need to first place in the list
                if (isFirstTime) {
                    startNode = endpointIds.get(groupIndex).get(0);
                    isFirstTime = false;
                }

                //mark checklist
                isGroupCheckList.put(groupIndex, true);
                //group the set
                tempGroup.addAll(endpointIds.get(groupIndex));
            }
            //remove first node
            tempGroup.remove(startNode);

            // new group
            List<String> newGroup = new ArrayList<>();
            newGroup.add(startNode);
            newGroup.addAll(tempGroup);

            //add to new list
            newListGroup.add(newGroup);
        }

        //add no need to group in to newListGroup
        for (Integer integer : isGroupCheckList.keySet()) {
            if (!isGroupCheckList.get(integer)) {
                newListGroup.add(endpointIds.get(integer));
            }
        }

        return newListGroup;
    }

    private List<List<String>> getremoveDuplicateShorterPath(List<List<String>> endpointIds) {
        List<List<String>> removeSet = new ArrayList<>();
        for (int i = 0; i < endpointIds.size(); i++) {
            List<String> endpointId = endpointIds.get(i);
            for (int j = i + 1; j < endpointIds.size(); j++) {
                List<String> endpointIds1 = endpointIds.get(j);

                String endpointNode = endpointId.get(0);
                String endpointNode1 = endpointIds1.get(0);
                if (endpointNode.equals(endpointNode1) &&
                        endpointId.containsAll(endpointIds1)) {
                    removeSet.add(endpointIds1);
                } else if (endpointNode1.equals(endpointNode) &&
                        endpointIds1.containsAll(endpointId)) {
                    removeSet.add(endpointId);
                }
            }
        }
        return removeSet;
    }


    private List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions() {
        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        LOGGER.info("all service names {}", serviceNames.toString());

        return serviceNames.stream().map(serviceName -> {
            return microserviceProjectDescriptionRepository
                    .findFirstByNameOrderByTimestampAsc(serviceName.getServiceName());
        }).collect(Collectors.toList());
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
