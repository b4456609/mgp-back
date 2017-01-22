package soselab.mpg.graph.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity(label = "Service")
public class ServiceNode {
    @GraphId
    private Long id;

    private String name;

    @Relationship(type = "OWN", direction = Relationship.UNDIRECTED)
    private Set<EndpointNode> endpointNodes;

    public ServiceNode() {
    }

    public ServiceNode(String name, Set<EndpointNode> endpointNodes) {
        this.name = name;
        this.endpointNodes = endpointNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EndpointNode> getEndpointNodes() {
        return endpointNodes;
    }

    public void setEndpointNodes(Set<EndpointNode> endpointNodes) {
        this.endpointNodes = endpointNodes;
    }
}
