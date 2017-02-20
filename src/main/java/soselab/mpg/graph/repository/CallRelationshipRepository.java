package soselab.mpg.graph.repository;

/**
 * Created by bernie on 2017/2/20.
 */

import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.graph.model.CallRelationship;

public interface CallRelationshipRepository extends GraphRepository<CallRelationship> {
}
