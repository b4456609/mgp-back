package soselab.mpg.graph.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ServiceWithEndpointPairItem {
    private String source;
    @ApiModelProperty("Class name in html")
    private String className;
    private String target;

    public ServiceWithEndpointPairItem() {
    }

    public ServiceWithEndpointPairItem(String source, String className, String target) {
        this.source = source;
        this.className = className;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "ServiceWithEndpointPairItem{" +
                "source='" + source + '\'' +
                ", className='" + className + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}