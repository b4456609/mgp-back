package soselab.mpg.graph.model;

import java.util.*;

/**
 * Created by Fan on 2017/1/26.
 */
public class PathGroup {
    private final List<List<String>> paths;
    private final Set<String> services;
    private boolean isCyclic;

    public PathGroup() {
        this.paths = new ArrayList<>();
        this.services = new HashSet<>();
        this.isCyclic = false;
    }

    public void addPath(List<String> path) {
        paths.add(path);
    }

    public void addServices(Collection<String> services) {
        this.services.addAll(services);
    }

    public List<List<String>> getPaths() {
        return paths;
    }

    public Set<String> getServices() {
        return services;
    }

    public boolean isContain(String id) {
        return paths.stream()
                .anyMatch(path -> path.contains(id)) || services.contains(id);
    }

    public boolean isFirstEndpoint(String endpoint) {
        if (paths.get(0) == null)
            return false;
        return paths.get(0).get(0).equals(endpoint);
    }

    public boolean isCyclic() {
        return isCyclic;
    }

    public boolean isServiceAndEndpoint(String id1, String id2) {
        if (services.contains(id1)) {
            return paths.stream().anyMatch(path -> path.contains(id2));
        }

        if (services.contains(id2)) {
            return paths.stream().anyMatch(path -> path.contains(id1));
        }

        return false;
    }

    public boolean isServiceCall(String service, String endpoint) {
        if (!services.contains(service)) return false;
        for (List<String> path : paths) {
            for (int i = 0, pathSize = path.size(); i < pathSize; i++) {
                String id = path.get(i);
                // the next item is endpoint is the service call
                if (id.contains(service) && i + 1 < pathSize && path.get(i + 1).equals(endpoint)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "PathGroup{" +
                "paths=" + paths +
                ", services=" + services +
                ", isCyclic=" + isCyclic +
                '}';
    }
}
