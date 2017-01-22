package soselab.mpg.graph.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceInfoControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {

    }

//    @Test
//    public void getServiceInfo() throws Exception {
//        restTemplate
//    }

}