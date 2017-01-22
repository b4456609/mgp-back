package soselab.mpg.pact.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.pact.controller.dto.PactConfigDTO;
import soselab.mpg.pact.controller.dto.PactDTO;
import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.service.PactService;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(path = "/api/pact")
public class PactController {

    private static Logger LOGGER = LoggerFactory.getLogger(PactController.class);

    @Autowired
    private PactService pactService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = "/config")
    public void setPact(@RequestBody PactConfigDTO pactConfigDTO) {
        LOGGER.info(pactConfigDTO.toString());
        PactConfig pactConfig = modelMapper.map(pactConfigDTO, PactConfig.class);
        pactService.setPactService(pactConfig);
    }

    @GetMapping(path = "/config")
    public PactConfigDTO getPactSetting() {
        PactConfig pactConfig = pactService.getPactConfig();
        PactConfigDTO dto = modelMapper.map(pactConfig, PactConfigDTO.class);
        return dto;
    }

    @GetMapping
    public List<PactDTO> getPacts() {
        pactService.getLatestPactFile();
        List<Pact> pacts = pactService.getPacts();
        LOGGER.info("all pact {}", pacts.toString());
        Type targetListType = new TypeToken<List<PactDTO>>() {
        }.getType();
        List<PactDTO> dtos = modelMapper.map(pacts, targetListType);
        return dtos;
    }
}
