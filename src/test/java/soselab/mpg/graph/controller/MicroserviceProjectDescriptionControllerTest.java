package soselab.mpg.graph.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.mpd.repository.MicroserviceProjectDescriptionRepository;
import soselab.mpg.mpd.repository.ServiceNameRepository;

/**
 * Created by bernie on 1/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MicroserviceProjectDescriptionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Autowired
    private ServiceNameRepository serviceNameRepository;


    @Before
    public void before() {
        microserviceProjectDescriptionRepository.deleteAll();
        serviceNameRepository.deleteAll();
    }

    @Test
    public void uploadMdpFiles() {
        UploadFile.uploadMdpFiles(restTemplate);
    }

}