package soselab.mpg.graph.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


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
    }

    @Test
    public void checkCyclicGroupsClassName() throws Exception {

        EndpointNode endpointNode = new EndpointNode("A / POST", "/", "POST");
        EndpointNode endpointNode1 = new EndpointNode("A / GET", "/", "GET");
        EndpointNode endpointNode2 = new EndpointNode("B / POST", "/", "POST");

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

        GraphDataDTO visualizationData = graphService.getVisualizationData();
        System.out.println(visualizationData);
        boolean cyclic = visualizationData.getNodes().stream().anyMatch(nodesItem -> nodesItem.getClassName()
                .contains("cyclic"));
        assertThat(cyclic).isTrue();
    }

}