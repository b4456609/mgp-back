package soselab.mpg.model.graph;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;

@RelationshipEntity(type = "CALL")
public class CallRelationship {
    @GraphId
    private Long id;
}
