package soselab.mpg.dto.graph;

/**
 * Created by JacksonGenerator on 2017/1/12.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class NodesItem {
    @JsonProperty("id")
    private String id;
    @JsonProperty("label")
    private String label;
    @JsonProperty("class")
    private String className;
    @JsonProperty("group")
    private Integer group;
}