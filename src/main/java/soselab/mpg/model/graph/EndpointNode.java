package soselab.mpg.model.graph;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity(label = "Endpoint")
public class EndpointNode {
    @GraphId
    private Long graphId;
    private String id;
    private String path;
    private String httpMethod;

    @Relationship(type = "CALL", direction = Relationship.OUTGOING)
    private Set<ServiceNode> serviceNodes;

    public EndpointNode() {
    }

    public EndpointNode(String id, String path, String httpMethod) {
        this.id = id;
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

    public Set<ServiceNode> getServiceNodes() {
        return serviceNodes;
    }

    public void setServiceNodes(Set<ServiceNode> serviceNodes) {
        this.serviceNodes = serviceNodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
