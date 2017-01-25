package soselab.mpg.graph.service;

import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;

import java.util.*;
import java.util.stream.Collectors;

public class PathAnalyzer {

    private final ServiceNodeRepository serviceNodeRepository;
    private final EndpointNodeRepository endpointNodeRepository;

    public PathAnalyzer(ServiceNodeRepository serviceNodeRepository, EndpointNodeRepository endpointNodeRepository) {
        this.serviceNodeRepository = serviceNodeRepository;
        this.endpointNodeRepository = endpointNodeRepository;
    }

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
}