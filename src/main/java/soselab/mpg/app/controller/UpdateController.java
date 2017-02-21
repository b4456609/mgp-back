package soselab.mpg.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.NoBDDProjectGitSettingException;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;
import soselab.mpg.pact.service.PactService;

import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/update")
public class UpdateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);
    private final BDDService bddService;
    private final MicroserviceGraphBuilderService microserviceGraphBuilderService;
    private final PactService pactService;

    @Autowired
    public UpdateController(BDDService bddService, MicroserviceGraphBuilderService microserviceGraphBuilderService, PactService pactService) {
        this.bddService = bddService;
        this.microserviceGraphBuilderService = microserviceGraphBuilderService;
        this.pactService = pactService;
    }

    @PostMapping
    public void updateAllData() throws InterruptedException {
        try {
            pactService.getLatestPactFile();
            boolean hasUpdate = bddService.updateProject();
            if (hasUpdate) {
                Future<Boolean> build = microserviceGraphBuilderService.build();
                while (!build.isDone()) {
                    Thread.sleep(500);
                }
            }
        } catch (NoBDDProjectGitSettingException e) {
            LOGGER.info("no bdd setting");
        }
    }

}
