package soselab.mpg.graph.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.graph.controller.dto.ServiceCallInformationDTO;
import soselab.mpg.graph.controller.dto.ServiceInformationDTO;
import soselab.mpg.graph.controller.dto.ServiceWithEndpointPairItem;
import soselab.mpg.graph.model.ServiceNode;

import java.util.List;


public interface ServiceNodeRepository extends GraphRepository<ServiceNode> {
    @Query("MATCH (s:Service)-[r:OWN]->(e:Endpoint) " +
            "RETURN s.name as source, e.endpointId as target")
    List<ServiceWithEndpointPairItem> getAllServiceWithEndpoint();

    @Query("MATCH (s:Service)-[:OWN]->(e:Endpoint) " +
            "OPTIONAL MATCH (e)-[:CALL]->(t:Endpoint) " +
            "RETURN COUNT(e) as endpointCount, s.name as id, COUNT(t) as serviceCallCount")
    List<ServiceInformationDTO> getServiceInfo();

    @Query("MATCH (s)<-[:OWN]-(consumer:Service), (s:Endpoint)-[:CALL]->(e:Endpoint), (e)<-[:OWN]-(provider:Service) " +
            "RETURN consumer.name as consumer, provider.name as provider")
    List<ServiceCallInformationDTO> getConsumerProvider();
}
