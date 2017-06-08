package soselab.mpg.graph.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "D3.js Data")
public class GraphDataDTO {
    @ApiModelProperty(value = "Relationship between Service and Endpoint")
    private List<ServiceWithEndpointPairItem> serviceWithEndpointPair;

    @ApiModelProperty(value = "D3.js Node including service, endpoint, scenario")
    private List<NodesItem> nodes;

    @ApiModelProperty(value = "Service Call Relation")
    private List<ProviderEndpointWithConsumerPairItem> providerEndpointWithConsumerPair;

    @ApiModelProperty(value = "Scenario Relation")
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