package soselab.mpg.bdd.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.bdd.client.dto.FeatureDTO;
import soselab.mpg.bdd.client.dto.LatestCommitStatusDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BDDClientTest {

    @Autowired
    BDDClient bddClient;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void clonetest() throws Exception {
        LatestCommitStatusDTO latestCommitStatusDTO = bddClient.gitClone("https://github.com/b4456609/easylearn-uat.git");
        assertThat(latestCommitStatusDTO).hasNoNullFieldsOrProperties();
    }

    @Test
    public void pull() throws Exception {
        LatestCommitStatusDTO pull = bddClient.pull();
        assertThat(pull).hasNoNullFieldsOrProperties();

    }

    @Test
    public void getLastestCommitStatus() throws Exception {
        LatestCommitStatusDTO lastestCommitStatus = bddClient.getLastestCommitStatus();
        assertThat(lastestCommitStatus).hasNoNullFieldsOrProperties();
    }

    @Test
    public void getParseData() throws Exception {
        List<FeatureDTO> parseData = bddClient.getParseData();
        System.out.println(parseData);
    }

}