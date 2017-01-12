package soselab.mpg.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphVisualizationFactoryTest {

    @Autowired
    MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Test
    public void create() throws Exception {
        List<MicroserviceProjectDescription> all = microserviceProjectDescriptionRepository.findAll();
        assert all.size() != 0;
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = Collections.singletonList(all.get(0));
        GraphVisualization graphVisualization = GraphVisualizationFactory.create(microserviceProjectDescriptions);
        System.out.println(graphVisualization);

    }

}