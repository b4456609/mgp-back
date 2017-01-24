package soselab.mpg.graph.controller.dto;

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

    public GraphVisualizationBuilder setProvicerEndpointWithConsumberPair(List<ProviderEndpointWithConsumerPairItem> serviceCall) {
        this.serviceCall = serviceCall;
        return this;
    }

    public GraphDataDTO createGraphVisualization() {
        return new GraphDataDTO(endpoints, nodes, serviceCall);
    }
}