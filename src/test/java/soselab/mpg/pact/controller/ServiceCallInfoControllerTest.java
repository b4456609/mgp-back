package soselab.mpg.pact.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.pact.controller.dto.PactDTO;
import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.service.PactService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceCallInfoControllerTest {
    @Autowired
    ServiceCallInfoController serviceCallInfoController;

    @MockBean
    private PactService pactService;

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        Pact pact = new Pact();
        pact.setConsumer("consum");
        pact.setProvider("pro");
        pact.setId("id");
        pact.setVersion("1.0.2");
        pact.setPact("pact content");
        List<Pact> ts = Collections.singletonList(pact);
        given(pactService.getPacts()).willReturn(ts);
    }

    @Test
    public void getPacts() throws Exception {
        List<PactDTO> pacts = serviceCallInfoController.getPacts();
        PactDTO pactDTO = pacts.get(0);
        System.out.println(pactDTO);
        assertThat(pactDTO.getProvider()).contains("pro");
        assertThat(pactDTO.getConsumer()).contains("consum");
        assertThat(pactDTO.getPact()).contains("content");
    }

    @Test
    public void testCall(){
        String forObject = restTemplate.getForObject("/api/serviceCallInfo", String.class);
        System.out.println(forObject);
        assertThat(forObject).contains("pro");
        assertThat(forObject).contains("content");
    }

}