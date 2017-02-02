package soselab.mpg.bdd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.bdd.client.BDDClient;
import soselab.mpg.bdd.client.dto.FeatureDTO;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BDDServiceTest {

    @Autowired
    private BDDService bddService;

    @MockBean
    private BDDClient bddClient;


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void parseProject() throws Exception, NoBDDProjectGitSettingException {
        String file = this.getClass().getResource("/parse.json").getPath();
        String s = new String(Files.readAllBytes(Paths.get(file)));
        FeatureDTO[] featureDTOS = objectMapper.readValue(s, FeatureDTO[].class);
        List<FeatureDTO> featureDTOS1 = Arrays.asList(featureDTOS);

        given(bddClient.getParseData()).willReturn(featureDTOS1);

        bddService.updateProject();

    }

}