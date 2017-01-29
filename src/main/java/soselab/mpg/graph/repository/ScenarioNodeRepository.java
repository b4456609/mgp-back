package soselab.mpg.graph.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.graph.model.ScenarioNode;

public interface ScenarioNodeRepository extends GraphRepository<ScenarioNode> {
}
