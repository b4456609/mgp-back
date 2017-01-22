package soselab.mpg.graph.controller.dto;

/**
 * Created by JacksonGenerator on 2017/1/12.
 */

import org.springframework.data.neo4j.annotation.QueryResult;


@QueryResult
public class ProviderEndpointWithConsumerPairItem {
    private String source;
    private String className;
    private String target;

    public ProviderEndpointWithConsumerPairItem() {
    }

    public ProviderEndpointWithConsumerPairItem(String source, String className, String target) {
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
        return "ProviderEndpointWithConsumerPairItem{" +
                "source='" + source + '\'' +
                ", className='" + className + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}