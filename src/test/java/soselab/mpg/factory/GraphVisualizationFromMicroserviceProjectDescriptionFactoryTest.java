package soselab.mpg.factory;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphVisualizationFromMicroserviceProjectDescriptionFactoryTest {

    @Autowired
    MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Autowired
    GraphVisualizationFromGraphFactory graphVisualizationFromGraphFactory;

//    @Test
//    public void create() throws Exception {
//        List<MicroserviceProjectDescription> all = microserviceProjectDescriptionRepository.findAll();
////        GraphVisualization graphVisualization = graphVisualizationFromGraphFactory.create(endpointNodes, serviceNodes, allServiceWithEndpoint, providerEndpointWithConsumerPairPair, pathNodeIdGroups);
//        System.out.println(graphVisualization);
//        System.out.println(graphVisualization.getNodes().size());
//        assertThat(graphVisualization.getNodes().size()).isEqualTo(17);
//        assertThat(graphVisualization.getServiceWithEndpointPair().size()).isEqualTo(13);
//    }

}