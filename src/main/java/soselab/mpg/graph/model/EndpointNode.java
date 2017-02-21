package soselab.mpg.graph.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Endpoint")
public class EndpointNode {
    @GraphId
    private Long graphId;

    private String endpointId;
    private String path;
    private String httpMethod;

    @Relationship(type = "CALL", direction = Relationship.OUTGOING)
    private Set<CallRelationship> callRelationships = new HashSet<>();

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

    public Set<CallRelationship> getCallRelationships() {
        return callRelationships;
    }

    public void setCallRelationships(Set<CallRelationship> callRelationships) {
        this.callRelationships = callRelationships;
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
