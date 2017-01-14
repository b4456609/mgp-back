package soselab.mpg.model.graph;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Endpoint")
public class EndpointNode {
    @GraphId
    private Long id;
    private String path;
    private String httpMethod;
}
