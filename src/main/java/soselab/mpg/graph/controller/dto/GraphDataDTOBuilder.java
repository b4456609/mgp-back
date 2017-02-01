package soselab.mpg.graph.controller.dto;

import java.util.List;

public class GraphDataDTOBuilder {
    private List<ServiceWithEndpointPairItem> serviceWithEndpointPair;
    private List<NodesItem> nodes;
    private List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPair;
    private List<ScenarioEndpointPairItem> scenarioEndpointPair;

    public GraphDataDTOBuilder setServiceWithEndpointPair(List<ServiceWithEndpointPairItem> serviceWithEndpointPair) {
        this.serviceWithEndpointPair = serviceWithEndpointPair;
        return this;
    }

    public GraphDataDTOBuilder setNodes(List<NodesItem> nodes) {
        this.nodes = nodes;
        return this;
    }

    public GraphDataDTOBuilder setProviderEndpointWithConsumerPair(List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPair) {
        this.providerEndpointWithConsumerPair = providerEndpointWithConsumerPair;
        return this;
    }

    public GraphDataDTOBuilder setScenarioEndpointPair(List<ScenarioEndpointPairItem> scenarioEndpointPair) {
        this.scenarioEndpointPair = scenarioEndpointPair;
        return this;
    }

    public GraphDataDTO createGraphDataDTO() {
        return new GraphDataDTO(serviceWithEndpointPair, nodes, providerEndpointWithConsumerPair, scenarioEndpointPair);
    }
}