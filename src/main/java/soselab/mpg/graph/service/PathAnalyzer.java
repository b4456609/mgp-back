package soselab.mpg.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.mpd.model.IDExtractor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PathAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathAnalyzer.class);

    private final EndpointNodeRepository endpointNodeRepository;

    public PathAnalyzer(EndpointNodeRepository endpointNodeRepository) {
        this.endpointNodeRepository = endpointNodeRepository;
    }

    public List<PathGroup> getPathNodeIdGroups() {
        List<List<String>> endpointIds = endpointNodeRepository.getPathEndpoints()
                .stream()
                //sort by first endpoint id
                .sorted(Comparator.comparing(a -> a.get(0)))
                .collect(Collectors.toList());
        LOGGER.debug("all path endpoint {}", endpointIds);

//        //translation to endpoint id string list
//        List<List<String>> endpointIds = pathEndpoints.stream()
//                .map(linkedHashMaps -> linkedHashMaps.stream()
//                        .map(linkedHashMap -> (String) linkedHashMap.get("endpointId"))
//                        .collect(Collectors.toList()))
//                //sort by first endpoint id
//                .sorted(Comparator.comparing(a -> a.get(0)))
//                .collect(Collectors.toList());
        LOGGER.debug("endpoint id {}", endpointIds);

        //remove the same start node but shorter path
        List<List<String>> removeSet = getRemoveDuplicateShorterPath(endpointIds);
        endpointIds.removeAll(removeSet);
        LOGGER.debug("removeset {}", removeSet);
        LOGGER.debug("endpoint id {}", endpointIds);

        //group paths start with same node, it is same endpoint
        List<PathGroup> pathGroups = groupThePathWithSameStartEndpoint(endpointIds);
        LOGGER.debug("get GroupSet id {}", pathGroups);

        // set service name
        List<PathGroup> serviceWithEndpointsGroup = setServiceAndCheckCyclic(pathGroups);
        LOGGER.debug("get GroupSet id {}", serviceWithEndpointsGroup);

        return serviceWithEndpointsGroup;
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
                } else if (!endpointNode1.equals(endpointNode)) {
                    i = j;
                    break;
                }
            }
        }
        return removeSet;
    }

    private List<PathGroup> setServiceAndCheckCyclic(List<PathGroup> groups) {
        return groups.stream()
                .map(group -> {
                    Set<String> collect = group.getPaths().stream()
                            .flatMap(path -> path.stream())
                            .map(IDExtractor::getServiceName)
                            .collect(Collectors.toSet());
                    group.addServices(collect);
                    LOGGER.debug("group {}", group);
                    return group;
                }).collect(Collectors.toList());
    }

    private List<PathGroup> groupThePathWithSameStartEndpoint(List<List<String>> endpointIds) {
        List<PathGroup> pathGroups = new ArrayList<>();
        int size = endpointIds.size();
        LOGGER.debug("size {}", size);
        for (int i = 0; i < size; i++) {
            LOGGER.debug("i {}", i);
            PathGroup pathGroup = new PathGroup();
            List<String> endpointId = endpointIds.get(i);
            pathGroup.addPath(endpointId);
            for (int j = i + 1; j < size; j++) {
                LOGGER.debug("i {} j {}", i, j);
                List<String> endpointIds1 = endpointIds.get(j);
                String endpointNode = endpointId.get(0);
                String endpointNode1 = endpointIds1.get(0);
                if (endpointNode.equals(endpointNode1)) {
                    pathGroup.addPath(endpointIds1);
                } else {
                    i = j - 1;
                    break;
                }
            }
            pathGroups.add(pathGroup);
        }
        return pathGroups;
    }
}