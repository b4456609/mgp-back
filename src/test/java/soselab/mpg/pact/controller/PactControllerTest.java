package soselab.mpg.pact.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PactControllerTest {

    @Autowired
    TestRestTemplate restTemplate;


    @Test
    public void setPact() throws Exception {
        String requestJson = "{\"url\":\"http://140.121.102.1\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        restTemplate.postForLocation("/api/pact/config", entity);

        String forObject = restTemplate.getForObject("/api/pact", String.class);
        assertThat(forObject).contains("http://140.121.102.1");
    }

    @Test
    public void getPacts() throws Exception {

        String response = restTemplate.getForObject("/api/pact", String.class);
        System.out.println(response);
        assertThat(response).contains("easylearn_pack");
    }

}