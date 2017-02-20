package soselab.mpg.graph.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Endpoint")
public class EndpointNode {
    @GraphId
    private Long graphId;

    private String endpointId;
    private String path;
    private String httpMethod;

    public EndpointNode() {
    }

    public EndpointNode(String endpointId, String path, String httpMethod) {
        this.endpointId = endpointId;
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EndpointNode that = (EndpointNode) o;

        if (!endpointId.equals(that.endpointId)) return false;
        if (!path.equals(that.path)) return false;
        return httpMethod.equals(that.httpMethod);
    }

    @Override
    public int hashCode() {
        int result = endpointId.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + httpMethod.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EndpointNode{" +
                "graphId=" + graphId +
                ", endpointId='" + endpointId + '\'' +
                ", path='" + path + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
