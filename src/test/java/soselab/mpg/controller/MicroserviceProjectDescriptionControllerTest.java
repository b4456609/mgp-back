package soselab.mpg.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;

import java.io.File;

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
        File file = new File(this.getClass().getResource("/mdp").getPath());
        File[] files = file.listFiles();
        System.out.println(files);
        for (File file1 : files) {
            uploadMdpFile(file1.getPath());
        }
    }

    private void uploadMdpFile(String filepath) {
        Resource resource = new FileSystemResource(filepath);
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        ResponseEntity<String> result = restTemplate.exchange("/api/mpd", HttpMethod.POST, requestEntity, String.class);
        System.out.println(result);
    }

}