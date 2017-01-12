package soselab.mpg.dto.graph;

/**
 * Created by JacksonGenerator on 2017/1/12.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class GraphVisualization {
    @JsonProperty("serviceEndpoints")
    private List<ServiceCallEndpointsPairItem> endpoints;
    @JsonProperty("nodes")
    private List<NodesItem> nodes;
    @JsonProperty("serviceCall")
    private List<ServiceCallItem> serviceCall;

    public GraphVisualization(List<ServiceCallEndpointsPairItem> endpoints, List<NodesItem> nodes, List<ServiceCallItem> serviceCall) {
        this.endpoints = endpoints;
        this.nodes = nodes;
        this.serviceCall = serviceCall;
    }

    public List<ServiceCallEndpointsPairItem> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<ServiceCallEndpointsPairItem> endpoints) {
        this.endpoints = endpoints;
    }

    public List<NodesItem> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesItem> nodes) {
        this.nodes = nodes;
    }

    public List<ServiceCallItem> getServiceCall() {
        return serviceCall;
    }

    public void setServiceCall(List<ServiceCallItem> serviceCall) {
        this.serviceCall = serviceCall;
    }

    @Override
    public String toString() {
        return "GraphVisualization{" +
                "endpoints=" + endpoints +
                ", nodes=" + nodes +
                ", serviceCall=" + serviceCall +
                '}';
    }
}