package soselab.mpg.app.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.NoBDDProjectGitSettingException;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UpdateControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    MicroserviceGraphBuilderService microserviceGraphBuilderService;

    @MockBean
    BDDService bddService;

    @Before
    public void setUp() throws Exception, NoBDDProjectGitSettingException {
        TestService testService = new TestService();
        given(microserviceGraphBuilderService.build())
                .willReturn(testService.serviceAsync());
        given(bddService.updateProject())
                .willReturn(true);
    }

    @Test
    public void testUpdateWouldBlockUpdateCall() throws Exception {
        Future<Boolean> build = microserviceGraphBuilderService.build();
        int count = 0;
        while (!build.isDone()) {
            restTemplate.postForLocation("/api/update", null);
            count++;
        }
        assertThat(count).isEqualTo(1);
    }

}

class TestService {
    private ExecutorService executorService = new ForkJoinPool();

    public Future<Boolean> serviceAsync() {
        return executorService.submit(() -> this.run());
    }

    private Boolean run() {
        try {
            Thread.sleep(3000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}