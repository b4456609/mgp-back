package soselab.mpg.graph.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.mpd.repository.MicroserviceProjectDescriptionRepository;
import soselab.mpg.mpd.repository.ServiceNameRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceInfoControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ServiceNodeRepository serviceNodeRepository;

    @Autowired
    EndpointNodeRepository endpointNodeRepository;

    @Autowired
    MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Autowired
    ServiceNameRepository serviceNameRepository;

    @Autowired
    GraphService graphService;

    @Before
    public void setUp() throws Exception {
        serviceNameRepository.deleteAll();
        endpointNodeRepository.deleteAll();
        microserviceProjectDescriptionRepository.deleteAll();
        serviceNodeRepository.deleteAll();

        UploadFile.uploadMdpFiles(restTemplate);
        graphService.buildGraphFromLatestMicroserviceProjectDescription();
    }

    @Test
    public void getServiceInfo() throws Exception {
        String response = restTemplate.getForObject("/api/serviceInfo", String.class);
        System.out.println(response);
    }

}