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
    private EndpointNode consumber;
    @EndNode
    private EndpointNode provider;

    public CallRelationship() {
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

    public EndpointNode getConsumber() {
        return consumber;
    }

    public void setConsumber(EndpointNode consumber) {
        this.consumber = consumber;
    }

    public EndpointNode getProvider() {
        return provider;
    }

    public void setProvider(EndpointNode provider) {
        this.provider = provider;
    }
}
