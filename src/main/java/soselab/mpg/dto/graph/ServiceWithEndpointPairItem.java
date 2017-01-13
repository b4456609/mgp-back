package soselab.mpg.dto.graph;

/**
 * Created by JacksonGenerator on 2017/1/12.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class ServiceWithEndpointPairItem {
    @JsonProperty("source")
    private String source;
    @JsonProperty("class")
    private String className;
    @JsonProperty("target")
    private String target;

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