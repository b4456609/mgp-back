package soselab.mpg.graph.service.handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.model.CallRelationship;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.repository.CallRelationshipRepository;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;

import java.util.List;

import static java.lang.Thread.sleep;
import static soselab.mpg.graph.controller.UploadFile.uploadMdpFiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ServiceBuildHandlerIntegressionTest {

    @Autowired
    private List<CrudRepository> graphRepositories;

    @Autowired
    private EndpointNodeRepository endpointNodeRepository;

    @Autowired
    private CallRelationshipRepository callRelationshipRepository;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MicroserviceGraphBuilderService microserviceGraphBuilderService;

    @Before
    public void setUp() throws Exception {
        graphRepositories.forEach(i -> i.deleteAll());
    }

    @Test
    public void testRelation() throws Exception {
        EndpointNode endpointNode = new EndpointNode("aa", "/a", "POST");
        EndpointNode endpointNode1 = new EndpointNode("aa1", "/a1", "POST1");
        CallRelationship callRelationship = new CallRelationship(true, endpointNode, endpointNode1);
        endpointNodeRepository.save(endpointNode);
        endpointNodeRepository.save(endpointNode1);
//        callRelationshipRepository.save(callRelationship);
    }

    @Test
    public void build() throws Exception {
        uploadMdpFiles(restTemplate);
//        microserviceGraphBuilderService.build();
        sleep(10000);
    }

}