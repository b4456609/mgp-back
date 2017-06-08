package soselab.mpg.app.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.app.dto.SettingDTO;
import soselab.mpg.bdd.service.BDDService;
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

    @ApiOperation(value = "Update Setting")
    @PostMapping
    public void updateSetting(@ApiParam(value = "setting object", required = true)
                              @RequestBody SettingDTO settingDTO) {
        LOGGER.debug("update setting {}", settingDTO);
        pactService.updatePactUrl(settingDTO.getPactHostUrl());
        bddService.updateGitUrl(settingDTO.getBddGitUrl());
    }

    @ApiOperation(value = "Get Setting")
    @GetMapping
    public SettingDTO getSetting() {
        String gitUrl = bddService.getGitUrl();
        String pactUrl = pactService.getPactGitUrl();
        return new SettingDTO(pactUrl, gitUrl);
    }
}
