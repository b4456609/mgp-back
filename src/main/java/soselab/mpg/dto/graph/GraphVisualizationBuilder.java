package soselab.mpg.dto.graph;

import java.util.List;

public class GraphVisualizationBuilder {
    private List<ServiceWithEndpointPairItem> endpoints;
    private List<NodesItem> nodes;
    private List<ProviderEndpointWithConsumerPairItem> serviceCall;

    public GraphVisualizationBuilder setServiceCallEndpointsPair(List<ServiceWithEndpointPairItem> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    public GraphVisualizationBuilder setNodes(List<NodesItem> nodes) {
        this.nodes = nodes;
        return this;
    }

    public GraphVisualizationBuilder setServiceCall(List<ProviderEndpointWithConsumerPairItem> serviceCall) {
        this.serviceCall = serviceCall;
        return this;
    }

    public GraphVisualization createGraphVisualization() {
        return new GraphVisualization(endpoints, nodes, serviceCall);
    }
}