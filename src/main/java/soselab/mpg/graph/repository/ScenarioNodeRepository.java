package soselab.mpg.graph.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.graph.model.ScenarioNode;

import java.util.List;

public interface ScenarioNodeRepository extends GraphRepository<ScenarioNode> {
    @Query("MATCH (s:Scenario)-[r:USE]->(e:Endpoint) " +
            "WHERE e.endpointId IN {0} " +
            "RETURN s.name")
    List<String> getScenarioByEndpoints(List<String> endpoints);
}
