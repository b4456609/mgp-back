package soselab.mpg.graph.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "USE")
public class UseRelationship {
    @GraphId
    private String id;

    public UseRelationship() {
    }
}
