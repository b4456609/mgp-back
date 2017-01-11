package soselab.mpg.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by bernie on 1/11/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MdpControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void uploadMdpFile() throws Exception {
        Resource resource = new FileSystemResource(
                "src/test/resources/mdp.json");
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        ResponseEntity<String> result = restTemplate.exchange("/api/mpd", HttpMethod.POST, requestEntity, String.class);
        System.out.println(result);
    }

}