package soselab.mpg.pact.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.pact.controller.dto.PactConfigDTO;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.service.PactService;

@RestController
@RequestMapping(path = "/api/pact")
public class PactController {

    @Autowired
    private PactService pactService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void setPact(PactConfigDTO pactConfigDTO) {
        PactConfig pactConfig = modelMapper.map(pactConfigDTO, PactConfig.class);
        pactService.setPactService(pactConfig);
    }

    @GetMapping
    public PactConfigDTO getPactSetting() {
        PactConfig pactConfig = pactService.getPactConfig();
        PactConfigDTO dto = modelMapper.map(pactConfig, PactConfigDTO.class);
        return dto;
    }
}
