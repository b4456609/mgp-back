package soselab.mpg.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.repository.neo4j.EndpointNodeRepository;
import soselab.mpg.repository.neo4j.ServiceNodeRepository;

/**
 * Created by bernie on 2017/1/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphServiceTest {

    @Autowired
    private EndpointNodeRepository endpointNodeRepository;

    @Autowired
    private ServiceNodeRepository serviceNodeRepository;

    @Autowired
    private GraphService graphService;

    @Test
    public void testRex() {
        String target = "easylearn-webback endpoint /note POST";
        String[] split = target.split(" ");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            System.out.println(s);
        }
    }

    @Test
    public void testConstructGraphDataBase() {
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();

        graphService.constructGrapht();
    }

}