package soselab.mpg.graph.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphServiceImplTest {

    @Autowired
    ServiceNodeRepository serviceNodeRepository;

    @Autowired
    EndpointNodeRepository endpointNodeRepository;

    @Autowired
    GraphService graphService;

    @Before
    public void setUp() throws Exception {
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();

        EndpointNode endpointNode = new EndpointNode("Aid / POST", "/", "POST");
        EndpointNode endpointNode1 = new EndpointNode("Aid1 / GET", "/", "GET");
        EndpointNode endpointNode2 = new EndpointNode("Bid / POST", "/", "POST");

        endpointNode.addServiceCallEndpoint(endpointNode2);
        endpointNode2.addServiceCallEndpoint(endpointNode1);

        endpointNodeRepository.save(Arrays.asList(endpointNode, endpointNode1, endpointNode2));

        Set<EndpointNode> endpointNodes = new HashSet<>();
        endpointNodes.add(endpointNode);
        endpointNodes.add(endpointNode1);

        Set<EndpointNode> endpointNodes1 = new HashSet<>();
        endpointNodes1.add(endpointNode2);

        ServiceNode a = new ServiceNode("A", endpointNodes);
        ServiceNode b = new ServiceNode("B", endpointNodes1);

        serviceNodeRepository.save(Arrays.asList(a, b));
    }

    @Test
    public void getCyclicGroups() throws Exception {
        List<List<String>> cyclicGroups = graphService.getCyclicGroups(graphService.getPathNodeIdGroups());
        System.out.println(cyclicGroups);
    }

}