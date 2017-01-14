package soselab.mpg.repository.neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.model.graph.EndpointNode;

/**
 * Created by bernie on 2017/1/14.
 */
public interface EndpointNodeRepository extends GraphRepository<EndpointNode> {
}
