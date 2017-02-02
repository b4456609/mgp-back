package soselab.mpg.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.app.dto.SettingDTO;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.pact.service.PactService;

@RestController
@RequestMapping("/api/setting")
public class SettingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);
    private final BDDService bddService;
    private final PactService pactService;

    @Autowired
    public SettingController(BDDService bddService, PactService pactService) {
        this.bddService = bddService;
        this.pactService = pactService;
    }

    @PostMapping
    public void updateSetting(@RequestBody SettingDTO settingDTO) {
        LOGGER.debug("update setting {}", settingDTO);
        bddService.updateGitUrl(settingDTO.getBddGitUrl());
        pactService.updatePactUrl(settingDTO.getPactHostUrl());
    }

    @GetMapping
    public SettingDTO getSetting() {
        String gitUrl = bddService.getGitUrl();
        String pactUrl = pactService.getPactGitUrl();
        return new SettingDTO(pactUrl, gitUrl);
    }
}
