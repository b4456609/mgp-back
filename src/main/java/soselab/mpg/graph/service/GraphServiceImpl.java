package soselab.mpg.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.graph.controller.dto.GraphVisualization;
import soselab.mpg.graph.controller.dto.ProviderEndpointWithConsumerPairItem;
import soselab.mpg.graph.controller.dto.ServiceInfoDTO;
import soselab.mpg.graph.controller.dto.ServiceWithEndpointPairItem;
import soselab.mpg.graph.controller.dto.factory.GraphVisualizationFromGraphFactory;
import soselab.mpg.graph.controller.dto.factory.ServiceEndpointIdFactory;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.model.Endpoint2ServiceCallDependency;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.service.MPDService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphServiceImpl implements GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphServiceImpl.class);

    private final GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory;
    private final MPDService mpdService;
    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;

    @Autowired
    public GraphServiceImpl(GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory,
                            MPDService mpdService, ServiceNodeRepository serviceNodeRepository,
                            EndpointNodeRepository endpointNodeRepository) {
        this.graphVisualizationFromGraphFactory = graphVisualizationFromGraphFactory;
        this.mpdService = mpdService;
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
    }

    @Override
    public GraphVisualization getVisualizationData() {
        buildGraphFromLatestMicroserviceProjectDescription();

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

    @Override
    public void buildGraphFromLatestMicroserviceProjectDescription() {
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();

        //get all latest service project description
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = mpdService
                .getMicroserviceProjectDescriptions();

        //all endpoint node
        Set<EndpointNode> allEndpointNodes = new HashSet<>();

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

    @Override
    public List<List<String>> getPathNodeIdGroups() {
        List<List<LinkedHashMap>> pathEndpoints = endpointNodeRepository.getPathEndpoints();

        //translation to endpoint id string list
        List<List<String>> endpointIds = pathEndpoints.stream()
                .map(linkedHashMaps -> linkedHashMaps.stream().map(linkedHashMap -> {
                    return (String) linkedHashMap.get("endpointId");
                }).collect(Collectors.toList()))
                .collect(Collectors.toList());


        List<List<String>> removeSet = getRemoveDuplicateShorterPath(endpointIds);
        endpointIds.removeAll(removeSet);

        //group set from different set
        List<List<String>> getGroupSet = getPathGroup(endpointIds);

        return getServiceWithEndpointsGroup(getGroupSet);
    }

    @Override
    public List<ServiceInfoDTO> getServiceInfo() {

        //get service node service call count and service endpoint count
        List<ServiceInfoDTO> serviceInfoDTOS = serviceNodeRepository.getServiceInfo();

        //get latest microservice description
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = mpdService.getMicroserviceProjectDescriptions();

        //get service name and swagger pair
        Map<String, String> collect = microserviceProjectDescriptions.stream()
                .collect(Collectors.toMap(MicroserviceProjectDescription::getName, MicroserviceProjectDescription::getSwagger));
        LOGGER.debug("service name and swagger pair {}", collect.toString());
        // insert swagger in to dto
        serviceInfoDTOS.forEach(serviceInfoDTO -> {
            serviceInfoDTO.setSwagger(collect.get(serviceInfoDTO.getId()));
        });
        return serviceInfoDTOS;
    }

    private List<List<String>> getServiceWithEndpointsGroup(List<List<String>> groups) {
        return groups.stream().map(group -> {
            Set<String> collect = group.stream()
                    .map(serviceNodeRepository::getServiceNameByEndpoint)
                    .collect(Collectors.toSet());
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
                    } else if (iGroup != null) {
                        jGroup.addAll(iGroup);
                        indexList2Group.put(i, jGroup);
                    } else {
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

    private List<List<String>> getRemoveDuplicateShorterPath(List<List<String>> endpointIds) {
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
