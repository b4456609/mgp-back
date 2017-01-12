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

    public NodesItem() {
    }

    public NodesItem(String id, String label, String className, Integer group) {
        this.id = id;
        this.label = label;
        this.className = className;
        this.group = group;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "NodesItem{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", className='" + className + '\'' +
                ", group=" + group +
                '}';
    }
}