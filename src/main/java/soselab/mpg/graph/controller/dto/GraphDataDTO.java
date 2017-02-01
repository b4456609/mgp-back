package soselab.mpg.graph.controller.dto;

import java.util.List;


public class GraphDataDTO {
    private List<ServiceWithEndpointPairItem> serviceWithEndpointPair;
    private List<NodesItem> nodes;
    private List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPair;
    private List<ScenarioEndpointPairItem> scenarioEndpointPair;

    public GraphDataDTO(List<ServiceWithEndpointPairItem> serviceWithEndpointPair, List<NodesItem> nodes, List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPair, List<ScenarioEndpointPairItem> scenarioEndpointPair) {
        this.serviceWithEndpointPair = serviceWithEndpointPair;
        this.nodes = nodes;
        this.providerEndpointWithConsumerPair = providerEndpointWithConsumerPair;
        this.scenarioEndpointPair = scenarioEndpointPair;
    }

    public List<ServiceWithEndpointPairItem> getServiceWithEndpointPair() {
        return serviceWithEndpointPair;
    }

    public void setServiceWithEndpointPair(List<ServiceWithEndpointPairItem> serviceWithEndpointPair) {
        this.serviceWithEndpointPair = serviceWithEndpointPair;
    }

    public List<NodesItem> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesItem> nodes) {
        this.nodes = nodes;
    }

    public List<ProviderEndpointWithConsumerPairItem> getProviderEndpointWithConsumerPair() {
        return providerEndpointWithConsumerPair;
    }

    public void setProviderEndpointWithConsumerPair(List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPair) {
        this.providerEndpointWithConsumerPair = providerEndpointWithConsumerPair;
    }

    public List<ScenarioEndpointPairItem> getScenarioEndpointPair() {
        return scenarioEndpointPair;
    }

    public void setScenarioEndpointPair(List<ScenarioEndpointPairItem> scenarioEndpointPair) {
        this.scenarioEndpointPair = scenarioEndpointPair;
    }

    @Override
    public String toString() {
        return "GraphDataDTO{" +
                "serviceWithEndpointPair=" + serviceWithEndpointPair +
                ", nodes=" + nodes +
                ", providerEndpointWithConsumerPair=" + providerEndpointWithConsumerPair +
                ", scenarioEndpointPair=" + scenarioEndpointPair +
                '}';
    }
}