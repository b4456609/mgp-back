package soselab.mpg.graph.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.graph.model.CallRelationship;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ServiceNode;
import soselab.mpg.graph.repository.CallRelationshipRepository;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.service.MPDService;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static soselab.mpg.Util.getResourceContent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ServiceBuildHandlerTest {

    @Autowired
    private ServiceBuildHandler serviceBuildHandler;

    @MockBean
    private MPDService mpdService;

    @MockBean
    private ServiceNodeRepository serviceNodeRepository;

    @Captor
    private ArgumentCaptor<Set<ServiceNode>> serviceNodeRepositoryArgumentCaptor;

    @MockBean
    private EndpointNodeRepository endpointNodeRepository;

    @Captor
    private ArgumentCaptor<EndpointNode> endpointNodeRepositoryArgumentCaptor;

    @MockBean
    private CallRelationshipRepository callRelationshipRepository;

    @Captor
    private ArgumentCaptor<List<CallRelationship>> callRelationshipRepositoryArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        String resourceContent0 = getResourceContent("/mpdGenTest/S0.json");
        String resourceContent1 = getResourceContent("/mpdGenTest/S1.json");
        String resourceContent2 = getResourceContent("/mpdGenTest/S2.json");
        ObjectMapper objectMapper = new ObjectMapper();
        MicroserviceProjectDescription microserviceProjectDescription = objectMapper
                .readValue(resourceContent0, MicroserviceProjectDescription.class);
        MicroserviceProjectDescription microserviceProjectDescription1 = objectMapper
                .readValue(resourceContent1, MicroserviceProjectDescription.class);
        MicroserviceProjectDescription microserviceProjectDescription2 = objectMapper
                .readValue(resourceContent2, MicroserviceProjectDescription.class);

        //return data
        given(mpdService.getMicroserviceProjectDescriptions())
                .willReturn(Lists.newArrayList(microserviceProjectDescription,
                        microserviceProjectDescription1, microserviceProjectDescription2));
    }

    @Test
    public void build() throws Exception {
        serviceBuildHandler.build();
        verify(serviceNodeRepository)
                .save(serviceNodeRepositoryArgumentCaptor.capture());
        Set<ServiceNode> value = serviceNodeRepositoryArgumentCaptor.getValue();
        assertThat(value.size()).isEqualTo(3);


        verify(callRelationshipRepository)
                .save(callRelationshipRepositoryArgumentCaptor.capture());
        List<CallRelationship> value1 = callRelationshipRepositoryArgumentCaptor.getValue();
        assertThat(value1.size()).isEqualTo(3);
        boolean isUnTest = value1.stream()
                .anyMatch(i -> i.isUnTest());
        assertThat(isUnTest).isTrue();
    }

}