package soselab.mpg.repository.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.dto.graph.ServiceWithEndpointPairItem;
import soselab.mpg.model.graph.ServiceNode;

import java.util.List;


public interface ServiceNodeRepository extends GraphRepository<ServiceNode> {
    @Query("MATCH (s:Service)-[r:OWN]->(e:Endpoint) " +
            "RETURN s.name as source, e.endpointId as target")
    List<ServiceWithEndpointPairItem> getAllServiceWithEndpoint();


    @Query("MATCH (s:Service)-[r:OWN]->(e:Endpoint) " +
            "WHERE e.endpointId={0} " +
            "RETURN s.name")
    String getServiceNameByEndpoint(String endpointId);
}
