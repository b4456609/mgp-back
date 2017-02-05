package soselab.mpg.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.NoBDDProjectGitSettingException;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;
import soselab.mpg.pact.service.PactService;

@RestController
@RequestMapping("/api/update")
public class UpdateController {

    private final BDDService bddService;
    private final MicroserviceGraphBuilderService microserviceGraphBuilderService;
    private final PactService pactService;

    @Autowired
    public UpdateController(BDDService bddService, MicroserviceGraphBuilderService microserviceGraphBuilderService, PactService pactService) {
        this.bddService = bddService;
        this.microserviceGraphBuilderService = microserviceGraphBuilderService;
        this.pactService = pactService;
    }

    @GetMapping
    public void updateAllData() {
        try {
            pactService.getLatestPactFile();
            boolean hasUpdate = bddService.updateProject();
            if (hasUpdate) microserviceGraphBuilderService.build();
        } catch (NoBDDProjectGitSettingException e) {
            e.printStackTrace();
        }
    }

}
