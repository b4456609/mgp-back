package soselab.mpg.repository.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import soselab.mpg.dto.graph.ProviderEndpointWithConsumerPairItem;
import soselab.mpg.model.graph.EndpointNode;

import java.util.LinkedHashMap;
import java.util.List;

public interface EndpointNodeRepository extends GraphRepository<EndpointNode> {

    @Query("MATCH p=(s:Service)-[r:OWN]->(e:Endpoint) " +
            "WHERE s.name={0} and e.httpMethod={1} and e.path={2} " +
            "RETURN e")
    EndpointNode findServiceEndpointByPathAndHttpMethod(String serviceName, String httpMethod, String path);

    @Query("MATCH p=(s:Endpoint)-[r:CALL]->(e:Endpoint), (e)<-[own:OWN]-(service:Service) " +
            "RETURN s.endpointId as source, service.name as target")
    List<ProviderEndpointWithConsumerPairItem> getProviderEndpointWithConsumerPairPair();

    @Query("MATCH p=(s:Endpoint)-[r:CALL*]->(e:Endpoint) " +
            "RETURN nodes(p)")
    List<List<LinkedHashMap>> getPathEndpoints();

}
