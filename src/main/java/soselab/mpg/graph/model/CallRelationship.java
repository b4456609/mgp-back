package soselab.mpg.graph.model;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "CALL")
public class CallRelationship {
    @GraphId
    private Long id;

    private boolean unTest;
    @StartNode
    private EndpointNode consumer;
    @EndNode
    private EndpointNode provider;

    public CallRelationship() {
    }

    public CallRelationship(boolean unTest, EndpointNode consumer, EndpointNode provider) {
        this.unTest = unTest;
        this.consumer = consumer;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUnTest() {
        return unTest;
    }

    public void setUnTest(boolean unTest) {
        this.unTest = unTest;
    }

    public EndpointNode getConsumer() {
        return consumer;
    }

    public void setConsumer(EndpointNode consumer) {
        this.consumer = consumer;
    }

    public EndpointNode getProvider() {
        return provider;
    }

    public void setProvider(EndpointNode provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "CallRelationship{" +
                "id=" + id +
                ", unTest=" + unTest +
                ", consumer=" + consumer +
                ", provider=" + provider +
                '}';
    }
}
