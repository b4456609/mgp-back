package soselab.mpg;

import org.junit.Test;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.service.MicroserviceProjectDescriptionReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by bernie on 1/11/17.
 */
public class MicroserviceProjectDescriptionReaderTest {
    @Test
    public void readMDP() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("src/test/resources/mdp.json")));
        assert !content.isEmpty();
        MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader = new MicroserviceProjectDescriptionReader();
        Optional<MicroserviceProjectDescription> mdp = microserviceProjectDescriptionReader.readMDP(content);
        assertThat(mdp.get()).hasNoNullFieldsOrProperties();
    }


    @Test(expected = RuntimeException.class)
    public void readMDPJsonNotRight() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("src/test/resources/mdp_error.json")));
        assert !content.isEmpty();
        MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader = new MicroserviceProjectDescriptionReader();
        Optional<MicroserviceProjectDescription> mdp = microserviceProjectDescriptionReader.readMDP(content);
        assertThat(mdp.isPresent()).isFalse();
    }

}