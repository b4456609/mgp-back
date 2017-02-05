package soselab.mpg.graph.service.handler;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.ScenarioWithTagDTO;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ScenarioNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ScenarioNodeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScenarioBuildHandlerTest {

    @MockBean
    BDDService bddService;

    @MockBean
    EndpointNodeRepository endpointNodeRepository;

    @Autowired
    ScenarioBuildHandler scenarioBuildHandler;

    @MockBean
    ScenarioNodeRepository scenarioNodeRepository;

    @Captor
    private ArgumentCaptor<List<ScenarioNode>> captor;
    private String name;
    private String id;

    @Before
    public void before() {

        endpointNodeRepository.deleteAll();
        scenarioNodeRepository.deleteAll();

        id = "ID";
        String endpointId = "service1 endpoint / POST";
        name = "scenario 1";
        ScenarioWithTagDTO scenarioWithTagDTO = new ScenarioWithTagDTO(id,
                name, Sets.newSet(endpointId));
        given(bddService.getScenarioWithTag())
                .willReturn(Lists.newArrayList(scenarioWithTagDTO));

        EndpointNode endpointNode = new EndpointNode(endpointId, "/", "POST");
        given((endpointNodeRepository.findAll()))
                .willReturn(Lists.newArrayList(endpointNode));
    }

    @Test
    public void build() throws Exception {
        scenarioBuildHandler.build();
        Iterable<ScenarioNode> all = scenarioNodeRepository.findAll();
        verify(scenarioNodeRepository, times(1))
                .save(captor.capture());

        System.out.println(captor.getAllValues());
        assertThat(captor.getValue().get(0)).hasFieldOrPropertyWithValue("name", name);
        assertThat(captor.getValue().get(0)).hasFieldOrPropertyWithValue("mongoId", id);
    }

}