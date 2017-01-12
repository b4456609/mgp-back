package soselab.mpg.dto.graph;

import java.util.List;

public class GraphVisualizationBuilder {
    private List<ServiceCallEndpointsPairItem> endpoints;
    private List<NodesItem> nodes;
    private List<ServiceCallItem> serviceCall;

    public GraphVisualizationBuilder setServiceCallEndpointsPair(List<ServiceCallEndpointsPairItem> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    public GraphVisualizationBuilder setNodes(List<NodesItem> nodes) {
        this.nodes = nodes;
        return this;
    }

    public GraphVisualizationBuilder setServiceCall(List<ServiceCallItem> serviceCall) {
        this.serviceCall = serviceCall;
        return this;
    }

    public GraphVisualization createGraphVisualization() {
        return new GraphVisualization(endpoints, nodes, serviceCall);
    }
}