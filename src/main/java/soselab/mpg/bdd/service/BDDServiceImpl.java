package soselab.mpg.bdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.bdd.client.BDDClient;
import soselab.mpg.bdd.client.dto.FeatureDTO;
import soselab.mpg.bdd.model.Feature;
import soselab.mpg.bdd.model.Scenario;
import soselab.mpg.bdd.repository.FeatureRepository;
import soselab.mpg.bdd.repository.ScenarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BDDServiceImpl implements BDDService {

    private final BDDClient bddClient;

    private final ScenarioRepository scenarioRepository;
    private final FeatureRepository featureRepository;

    @Autowired
    public BDDServiceImpl(BDDClient bddClient, ScenarioRepository scenarioRepository,
                          FeatureRepository featureRepository) {
        this.bddClient = bddClient;
        this.scenarioRepository = scenarioRepository;
        this.featureRepository = featureRepository;
    }

    @Override
    public void parseProject() {
        scenarioRepository.deleteAll();
        featureRepository.deleteAll();

        List<FeatureDTO> parseData = bddClient.getParseData();
        List<Scenario> scenarioList = parseData.stream().flatMap(featureDTO -> {

            Feature feature = new Feature(featureDTO.getFeature(), featureDTO.getContent());
            featureRepository.save(feature);
            List<Scenario> scenarios = featureDTO.getScenario().stream()
                    .map(scenarioBean ->
                            new Scenario(scenarioBean.getName(), scenarioBean.getLine(),
                                    scenarioBean.getTags(), feature))
                    .collect(Collectors.toList());

            return scenarios.stream();
        }).collect(Collectors.toList());

        scenarioRepository.save(scenarioList);
    }
}
