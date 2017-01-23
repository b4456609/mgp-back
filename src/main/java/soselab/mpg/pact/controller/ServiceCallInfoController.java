package soselab.mpg.pact.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.pact.controller.dto.PactDTO;
import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.service.PactService;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/serviceCallInfo")
public class ServiceCallInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCallInfoController.class);

    private final PactService pactService;
    private final ModelMapper modelMapper;

    @Autowired
    public ServiceCallInfoController(PactService pactService, ModelMapper modelMapper) {
        this.pactService = pactService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PactDTO> getPacts() {
        pactService.getLatestPactFile();
        List<Pact> pacts = pactService.getPacts();
        LOGGER.info("all pact {}", pacts.toString());
        Type targetListType = new TypeToken<List<PactDTO>>() {
        }.getType();
        return modelMapper.map(pacts, targetListType);
    }
}
