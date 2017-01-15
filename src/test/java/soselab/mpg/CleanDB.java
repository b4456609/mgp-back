package soselab.mpg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;
import soselab.mpg.repository.neo4j.EndpointNodeRepository;
import soselab.mpg.repository.neo4j.ServiceNodeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CleanDB {
    @Autowired
    ServiceNodeRepository serviceNodeRepository;

    @Autowired
    ServiceNameRepository serviceNameRepository;

    @Autowired
    EndpointNodeRepository endpointNodeRepository;

    @Autowired
    MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Test
    public void deleteAll() {
        serviceNameRepository.deleteAll();
        serviceNodeRepository.deleteAll();
        endpointNodeRepository.deleteAll();
        microserviceProjectDescriptionRepository.deleteAll();
    }
}
