package soselab.mpg.graph.controller.dto.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.controller.GraphController;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;

import java.util.concurrent.Future;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static soselab.mpg.graph.controller.UploadFile.uploadMdpFilesFromDir;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GraphVisualizationFromGraphFactoryTest {

    @Autowired
    private GraphController graphController;

    @Autowired
    private MicroserviceGraphBuilderService microserviceGraphBuilderService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testCyclic() throws JsonProcessingException, InterruptedException {
        uploadMdpFilesFromDir(restTemplate, "mpdCyclic");
        Future<Boolean> build = microserviceGraphBuilderService.build();
        while (!build.isDone()) {
            Thread.sleep(100);
        }
        GraphDataDTO graphData = graphController.getGraphData();
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(graphData);
        System.out.println(s);

        assertThat(s).contains("cyclic");
    }

}