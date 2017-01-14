package soselab.mpg.model.graph;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity(label = "Service")
public class ServiceNode {
    @GraphId
    private Long id;

    @Relationship(type = "OWN", direction = Relationship.UNDIRECTED)
    private Set<EndpointNode> endpointNodes;

    @Relationship(type = "CALL", direction = Relationship.OUTGOING)
    private Set<ServiceNode> serviceNodes;
}
