package soselab.mpg.graph.service;

import soselab.mpg.graph.model.CyclicType;
import soselab.mpg.graph.model.PathGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static soselab.mpg.mpd.model.IDExtractor.getServiceName;


public class CyclicAnalyzer {
    public static void analyze(List<PathGroup> groups) {
        groups.forEach(group -> {
            // check group whether cyclic or not
            boolean isCyclic = group.getPaths()
                    .stream()
                    .anyMatch(path -> {
                        Set<String> allItems = new HashSet<>();
                        for (String id : path) {
                            String serviceNameByEndpoint = getServiceName(id);
                            if (!allItems.add(serviceNameByEndpoint)) {
                                return true;
                            }
                        }
                        return false;
                    });
            if (isCyclic) {
                // check cyclic type
                boolean isEnhanceCyclic = group.getPaths()
                        .stream()
                        .anyMatch(path -> {
                            Set<String> allItems = new HashSet<>(path);
                            return allItems.size() < path.size();
                        });

                group.setCyclicType(isEnhanceCyclic ? CyclicType.STRONG : CyclicType.WEEK);
            }
        });
    }
}

