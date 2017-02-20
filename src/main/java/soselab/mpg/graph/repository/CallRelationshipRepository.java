package soselab.mpg.graph.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.graph.model.CallRelationship;
import soselab.mpg.graph.model.UnTestServiceCall;

import java.util.List;

public interface CallRelationshipRepository extends GraphRepository<CallRelationship> {
    @Query("MATCH (s:Endpoint)-[call:CALL]->(e:Endpoint), (service:Service) -[OWN]-> (s)" +
            "WHERE call.unTest=true " +
            "RETURN service.name as from, e.endpointId as to")
    public List<UnTestServiceCall> getUnTestServiceCall();
}
