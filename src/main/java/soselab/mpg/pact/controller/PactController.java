package soselab.mpg.pact.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.pact.controller.dto.PactConfigDTO;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.service.PactService;

@RestController
@RequestMapping(path = "/api/pact")
public class PactController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PactController.class);

    private final PactService pactService;
    private final ModelMapper modelMapper;

    @Autowired
    public PactController(PactService pactService, ModelMapper modelMapper) {
        this.pactService = pactService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/config")
    public void setPact(@RequestBody PactConfigDTO pactConfigDTO) {
        LOGGER.info(pactConfigDTO.toString());
        PactConfig pactConfig = modelMapper.map(pactConfigDTO, PactConfig.class);
        pactService.setPactService(pactConfig);
    }

    @GetMapping(path = "/config")
    public PactConfigDTO getPactSetting() {
        PactConfig pactConfig = pactService.getPactConfig();
        return modelMapper.map(pactConfig, PactConfigDTO.class);
    }
}
