package soselab.mpg.pact.controller.dto;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import soselab.mpg.pact.model.PactConfig;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by bernie on 2017/1/21.
 */
public class ServiceCallRelationInformationConfigDTOTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testDTO() {
        PactConfig pactConfig = new PactConfig();
        pactConfig.setId("id");
        pactConfig.setUrl("test");

        PactConfigDTO map = modelMapper.map(pactConfig, PactConfigDTO.class);
        assertThat(map).hasFieldOrPropertyWithValue("url", "test");
        System.out.println(map);
    }

    @Test
    public void testDTOtoObject() {
        PactConfigDTO pactConfig = new PactConfigDTO();
        pactConfig.setUrl("test");

        PactConfig map = modelMapper.map(pactConfig, PactConfig.class);
        assertThat(map).hasFieldOrPropertyWithValue("url", "test");
        System.out.println(map);
    }
}