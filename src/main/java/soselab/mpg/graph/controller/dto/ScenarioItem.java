package soselab.mpg.graph.controller.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Fan on 2017/2/2.
 */
public class ScenarioItem {
    private String id;
    private String name;
    private String content;
    @ApiModelProperty(value = "Feature file id")
    private String featureId;

    public ScenarioItem(String id, String name, String content, String featureId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.featureId = featureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }
}
