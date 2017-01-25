package soselab.mpg.pact.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.pact.model.PactConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceCallRelationInformationServiceImpTest {
    @Autowired
    private PactService pactService;

    @Test
    public void getLatestPactFile() throws Exception {
        PactConfig pactConfig = new PactConfig();
        pactConfig.setUrl("http://140.121.102.161:8880/");
        pactService.setPactService(pactConfig);

        pactService.getLatestPactFile();
    }

}