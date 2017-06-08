package soselab.mpg.bdd.controller;


import io.swagger.annotations.ApiModelProperty;

public class FeatureDocumentDTO {
    @ApiModelProperty(value = "Feature name")
    private String name;
    @ApiModelProperty(value = "Feature content")
    private String content;

    public FeatureDocumentDTO(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
