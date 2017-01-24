package soselab.mpg.graph.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by bernie on 1/13/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphDataDTOControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getGraphData() throws Exception {
        String body = restTemplate.getForEntity("/api/dto", String.class).getBody();
        System.out.println(body);
    }

}