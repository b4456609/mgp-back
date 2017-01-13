package soselab.mpg.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphVisualizationFactoryTest {

    @Autowired
    MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Test
    public void create() throws Exception {
        List<MicroserviceProjectDescription> all = microserviceProjectDescriptionRepository.findAll();
        GraphVisualization graphVisualization = GraphVisualizationFactory.create(all);
        System.out.println(graphVisualization);
        System.out.println(graphVisualization.getNodes().size());
        assertThat(graphVisualization.getNodes().size()).isEqualTo(17);
        assertThat(graphVisualization.getServiceWithEndpointPair().size()).isEqualTo(13);
    }

}