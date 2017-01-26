package soselab.mpg.graph.service;

import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.repository.ServiceNodeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CyclicAnalyzer {

    private final ServiceNodeRepository serviceNodeRepository;

    public CyclicAnalyzer(ServiceNodeRepository serviceNodeRepository) {
        this.serviceNodeRepository = serviceNodeRepository;
    }

    public void analyze(List<PathGroup> groups) {
        for (PathGroup group : groups) {
            for (List<String> path : group.getPaths()) {
                Set<String> allItems = new HashSet<>();
                for (String id : path) {
                    String serviceNameByEndpoint = serviceNodeRepository.getServiceNameByEndpoint(id);
                    if (!allItems.add(serviceNameByEndpoint)) {
                        group.setCyclic(true);
                    }
                }
            }
        }
    }
}
