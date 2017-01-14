package soselab.mpg.model.graph;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "OWN")
public class OwnRelationship {
    @GraphId
    private Long id;

    public OwnRelationship() {
    }
}
