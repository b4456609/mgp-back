package soselab.mpg.graph.controller.dto;

import io.swagger.annotations.ApiModelProperty;

public class ScenarioEndpointPairItem {
    private String source;
    private String target;
    @ApiModelProperty("Class name in html")
    private String className;

    public ScenarioEndpointPairItem(String source, String target, String className) {
        this.source = source;
        this.target = target;
        this.className = className;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ScenarioEndpointPairItem{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
