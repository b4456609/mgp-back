package soselab.mpg.graph.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.controller.GraphController;
import soselab.mpg.graph.controller.UploadFile;
import soselab.mpg.graph.repository.CallRelationshipRepository;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
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
@ActiveProfiles("log")
public class MicroserviceGraphBuilderServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceGraphBuilderServiceImplTest.class);
    @MockBean
    MPDService mpdService;
    @Autowired
    ServiceBuildHandler serviceBuildHandler;
    @Autowired
    MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader;
    @Autowired
    GraphController graphController;
    private int[] numData = {5,10,20,30,40,50,100,200,300,400,500,1000,2000,3000,4000,5000};

    @Autowired
    private ServiceNodeRepository serviceNodeRepository;
    @Autowired
    private EndpointNodeRepository endpointNodeRepository;
    @Autowired
    private CallRelationshipRepository callRelationshipRepository;

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


        for (int j = 0; j < 20; j++){
            serviceNodeRepository.deleteAll();
            endpointNodeRepository.deleteAll();
            callRelationshipRepository.deleteAll();
            LOGGER.info("start {}th", j);
            for (int i = 0; i <numData.length; i++){
                int num = numData[i];
                given(mpdService.getMicroserviceProjectDescriptions())
                        .willReturn(microserviceProjectDescriptions.subList(0, num));
                LOGGER.info("start {}th, num {}", i, num);
                long start = System.currentTimeMillis();
                serviceBuildHandler.build();
                graphController.getGraphData();
                long end = System.currentTimeMillis();
                LOGGER.info("{}th time: {}ms", i, end - start);
            }

        }

    }

}