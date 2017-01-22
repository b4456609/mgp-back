package soselab.mpg.neo4j;

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
import soselab.mpg.graph.service.GraphService;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PathTest {
    @Autowired
    ServiceNodeRepository serviceNodeRepository;
    @Autowired
    EndpointNodeRepository endpointNodeRepository;
    private int count = 1;

    @Autowired
    private GraphService graphService;

    @Before
    public void setUp() throws Exception {
        //setTestData();
//        MicroserviceProjectDescriptionControllerTest microserviceProjectDescriptionControllerTest = new MicroserviceProjectDescriptionControllerTest();
//        microserviceProjectDescriptionControllerTest.uploadMdpFiles();
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();
        graphService.buildGraphFromLatestMicroserviceProjectDescription();
    }

    private void setTestData() {
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
        endpointNodeSet.add(new EndpointNode("id" + count, "path" + count, "httpmethod" + count));
        endpointNodeSet.add(new EndpointNode("id1" + count, "path1" + count, "httpmethod1" + count));
        count++;
        return new ServiceNode(serviceName, endpointNodeSet);
    }

    @Test
    public void testPath() {

        long start = System.currentTimeMillis();

        List<List<String>> pathNodeIdGroups = graphService.getPathNodeIdGroups();
        pathNodeIdGroups.forEach(System.out::println);

        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void findServiceEndpointByPathAndHttpMethod() throws Exception {
        List<List<LinkedHashMap>> pathEndpoints = endpointNodeRepository.getPathEndpoints();

        List<List<String>> endpointIds = pathEndpoints.stream()
                .map(linkedHashMaps -> {
                    return linkedHashMaps.stream().map(linkedHashMap -> {
                        return (String) linkedHashMap.get("endpointId");
                    }).collect(Collectors.toList());
                })
                .collect(Collectors.toList());


        List<List<String>> removeSet = new ArrayList<>();
        for (int i = 0; i < endpointIds.size(); i++) {
            List<String> endpointId = endpointIds.get(i);
            for (int j = i + 1; j < endpointIds.size(); j++) {
                List<String> endpointIds1 = endpointIds.get(j);

                String endpointNode = endpointId.get(0);
                String endpointNode1 = endpointIds1.get(0);
                if (endpointNode.equals(endpointNode1) &&
                        endpointId.containsAll(endpointIds1)) {
                    removeSet.add(endpointIds1);
                } else if (endpointNode1.equals(endpointNode) &&
                        endpointIds1.containsAll(endpointId)) {
                    removeSet.add(endpointId);
                }
            }
        }
        endpointIds.forEach(System.out::println);
        System.out.println();
        removeSet.forEach(System.out::println);

//        System.out.println(pathEndpoints);
//        System.out.println(removeSet);
    }

    private boolean isGarbage(List<EndpointNode> endpointNode1, List<EndpointNode> endpointNode2) {
        return endpointNode1.get(0).equals(endpointNode2.get(0)) &&
                endpointNode1.containsAll(endpointNode2);
    }
}
