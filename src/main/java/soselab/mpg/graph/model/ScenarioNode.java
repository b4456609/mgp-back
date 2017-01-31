package soselab.mpg.graph.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity(label = "Scenario")
public class ScenarioNode {
    @GraphId
    private Long id;

    private String name;
    private String mongoId;

    @Relationship(type = "USE", direction = Relationship.UNDIRECTED)
    private Set<EndpointNode> endpointNodes;

    public ScenarioNode() {
    }

    public ScenarioNode(String name, String mongoId, Set<EndpointNode> endpointNodes) {
        this.name = name;
        this.mongoId = mongoId;
        this.endpointNodes = endpointNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public Set<EndpointNode> getEndpointNodes() {
        return endpointNodes;
    }

    public void setEndpointNodes(Set<EndpointNode> endpointNodes) {
        this.endpointNodes = endpointNodes;
    }

    @Override
    public String toString() {
        return "ScenarioNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mongoId='" + mongoId + '\'' +
                ", endpointNodes=" + endpointNodes +
                '}';
    }
}
