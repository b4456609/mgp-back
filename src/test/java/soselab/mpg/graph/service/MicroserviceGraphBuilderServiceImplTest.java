package soselab.mpg.graph.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.controller.UploadFile;
import soselab.mpg.graph.service.handler.ServiceBuildHandler;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.service.MPDService;
import soselab.mpg.mpd.service.MicroserviceProjectDescriptionReader;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MicroserviceGraphBuilderServiceImplTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    MPDService mpdService;

    @Autowired
    ServiceBuildHandler serviceBuildHandler;

    @Autowired
    MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader;

    @Test
    public void build() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(UploadFile.class.getResource("/output").getPath());
        File[] files = file.listFiles();
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = new ArrayList<>();
        for (File file1 : files) {
            byte[] bytes = Files.readAllBytes(file1.toPath());
            MicroserviceProjectDescription microserviceProjectDescription = microserviceProjectDescriptionReader
                    .readMDP(new String(bytes)).orElseThrow(Exception::new);
            microserviceProjectDescriptions.add(microserviceProjectDescription);
        }
        System.out.println(microserviceProjectDescriptions.size());


        given(mpdService.getMicroserviceProjectDescriptions())
                .willReturn(microserviceProjectDescriptions);

        serviceBuildHandler.build();


    }

}