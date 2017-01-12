package soselab.mpg.dto.graph;

/**
 * Created by JacksonGenerator on 2017/1/12.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class EndpointsItem {
    @JsonProperty("source")
    private String source;
    @JsonProperty("class")
    private String className;
    @JsonProperty("target")
    private String target;
}