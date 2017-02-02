package soselab.mpg.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.app.dto.SettingDTO;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.NoBDDProjectGitSettingException;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;
import soselab.mpg.pact.service.PactService;

@RestController
@RequestMapping("/api/setting")
public class SettingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);
    private final BDDService bddService;
    private final PactService pactService;
    private final MicroserviceGraphBuilderService microserviceGraphBuilderService;

    @Autowired
    public SettingController(BDDService bddService, PactService pactService, MicroserviceGraphBuilderService microserviceGraphBuilderService) {
        this.bddService = bddService;
        this.pactService = pactService;
        this.microserviceGraphBuilderService = microserviceGraphBuilderService;
    }

    @PostMapping
    public void updateSetting(@RequestBody SettingDTO settingDTO) {
        LOGGER.debug("update setting {}", settingDTO);
        boolean success = bddService.updateGitUrl(settingDTO.getBddGitUrl());
        pactService.updatePactUrl(settingDTO.getPactHostUrl());

        LOGGER.debug("updateGitUrl {}", success);
        //success need to update graph
        if (success) {
            try {
                bddService.parseProject();
                microserviceGraphBuilderService.build();
            } catch (NoBDDProjectGitSettingException e) {
                LOGGER.error("NoBDDProjectGitSettingException {}", e);
            }
        }
    }

    @GetMapping
    public SettingDTO getSetting() {
        String gitUrl = bddService.getGitUrl();
        String pactUrl = pactService.getPactGitUrl();
        return new SettingDTO(pactUrl, gitUrl);
    }
}
