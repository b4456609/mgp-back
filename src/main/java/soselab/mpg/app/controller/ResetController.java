package soselab.mpg.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.repository.BDDGitSettingRepository;
import soselab.mpg.bdd.repository.FeatureRepository;
import soselab.mpg.bdd.repository.ScenarioRepository;
import soselab.mpg.graph.repository.CallRelationshipRepository;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ScenarioNodeRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.repository.MicroserviceProjectDescriptionRepository;
import soselab.mpg.mpd.repository.ServiceNameRepository;
import soselab.mpg.pact.repository.PactConfigRepository;
import soselab.mpg.pact.repository.PactRepository;
import soselab.mpg.testreader.repository.TestReportRepository;

@RestController
@RequestMapping("/api/reset")
public class ResetController {

    private final BDDGitSettingRepository bddGitSettingRepository;
    private final FeatureRepository featureRepository;
    private final ScenarioRepository scenarioRepository;
    private final CallRelationshipRepository callRelationshipRepository;
    private final EndpointNodeRepository endpointNodeRepository;
    private final ScenarioNodeRepository scenarioNodeRepository;
    private final ServiceNodeRepository serviceNodeRepository;
    private final MicroserviceProjectDescriptionRepository microserviceGraphBuilderService;
    private final ServiceNameRepository serviceNameRepository;
    private final PactConfigRepository pactConfigRepository;
    private final PactRepository pactRepository;
    private final TestReportRepository testReportRepository;

    @Autowired
    public ResetController(BDDGitSettingRepository bddGitSettingRepository, FeatureRepository featureRepository,
                           ScenarioRepository scenarioRepository, CallRelationshipRepository callRelationshipRepository,
                           EndpointNodeRepository endpointNodeRepository, ScenarioNodeRepository scenarioNodeRepository,
                           ServiceNodeRepository serviceNodeRepository,
                           MicroserviceProjectDescriptionRepository microserviceGraphBuilderService,
                           ServiceNameRepository serviceNameRepository, PactConfigRepository pactConfigRepository,
                           PactRepository pactRepository, TestReportRepository testReportRepository) {
        this.bddGitSettingRepository = bddGitSettingRepository;
        this.featureRepository = featureRepository;
        this.scenarioRepository = scenarioRepository;
        this.callRelationshipRepository = callRelationshipRepository;
        this.endpointNodeRepository = endpointNodeRepository;
        this.scenarioNodeRepository = scenarioNodeRepository;
        this.serviceNodeRepository = serviceNodeRepository;
        this.microserviceGraphBuilderService = microserviceGraphBuilderService;
        this.serviceNameRepository = serviceNameRepository;
        this.pactConfigRepository = pactConfigRepository;
        this.pactRepository = pactRepository;
        this.testReportRepository = testReportRepository;
    }

    @DeleteMapping
    public void reset() {
        this.bddGitSettingRepository.deleteAll();
        this.featureRepository.deleteAll();
        this.scenarioRepository.deleteAll();
        this.callRelationshipRepository.deleteAll();
        this.endpointNodeRepository.deleteAll();
        this.scenarioNodeRepository.deleteAll();
        this.serviceNodeRepository.deleteAll();
        this.serviceNameRepository.deleteAll();
        this.pactConfigRepository.deleteAll();
        this.pactRepository.deleteAll();
        this.testReportRepository.deleteAll();
        this.microserviceGraphBuilderService.deleteAll();
    }
}
