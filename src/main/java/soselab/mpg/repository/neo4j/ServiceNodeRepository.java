package soselab.mpg.repository.neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.model.graph.ServiceNode;


public interface ServiceNodeRepository extends GraphRepository<ServiceNode> {
}
