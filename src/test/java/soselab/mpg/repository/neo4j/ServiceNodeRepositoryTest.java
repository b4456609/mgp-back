package soselab.mpg.repository.neo4j;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.model.graph.EndpointNode;
import soselab.mpg.model.graph.ServiceNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceNodeRepositoryTest {

    @Autowired
    ServiceNodeRepository serviceNodeRepository;
    @Autowired
    EndpointNodeRepository endpointNodeRepository;
    private int count = 1;

    @Before
    public void setUp() throws Exception {
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();

        List<ServiceNode> serviceNodes = new ArrayList<>();

        ServiceNode a = buildANode("A");
        serviceNodes.add(a);

        ServiceNode b = buildANode("B");
        serviceNodes.add(b);

        ServiceNode c = buildANode("C");
        serviceNodes.add(c);


        EndpointNode endpointNode = null;
        for (EndpointNode item : a.getEndpointNodes()) {
            endpointNode = item;
            break;
        }

        for (EndpointNode item : b.getEndpointNodes()) {
            endpointNode.addServiceCallEndpoint(item);
            endpointNode = item;
            break;
        }

        for (EndpointNode item : c.getEndpointNodes()) {
            endpointNode.addServiceCallEndpoint(item);
            break;
        }

        serviceNodeRepository.save(serviceNodes);
    }

    private ServiceNode buildANode(String serviceName) {
        Set<EndpointNode> endpointNodeSet = new HashSet<>();
        endpointNodeSet.add(new EndpointNode("id", "path" + count, "httpmethod" + count));
        endpointNodeSet.add(new EndpointNode("id1", "path1" + count, "httpmethod1" + count));
        count++;
        return new ServiceNode(serviceName, endpointNodeSet);
    }

    @Test
    public void findServiceEndpointByPathAndHttpMethod() throws Exception {
        EndpointNode endpoint = endpointNodeRepository
                .findServiceEndpointByPathAndHttpMethod("A", "httpmethod1", "path1");
        Iterable<EndpointNode> all = endpointNodeRepository.findAll();
        System.out.println(endpoint);
        assertThat(endpoint.getEndpointId()).isEqualTo("id");
        assertThat(endpoint.getPath()).isEqualToIgnoringCase("path1");
        assertThat(endpoint.getHttpMethod()).isEqualToIgnoringCase("httpmethod1");
    }

}