package soselab.mpg.dto.graph;

/**
 * Created by JacksonGenerator on 2017/1/12.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class GraphVisualization {
    @JsonProperty("endpoints")
    private List<EndpointsItem> endpoints;
    @JsonProperty("nodes")
    private List<NodesItem> nodes;
    @JsonProperty("serviceCall")
    private List<ServiceCallItem> serviceCall;
}