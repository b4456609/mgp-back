package soselab.mpg.bdd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.bdd.client.BDDClient;
import soselab.mpg.bdd.client.dto.FeatureDTO;
import soselab.mpg.bdd.client.dto.LatestCommitStatusDTO;
import soselab.mpg.bdd.model.BDDGitSetting;
import soselab.mpg.bdd.model.Feature;
import soselab.mpg.bdd.model.Scenario;
import soselab.mpg.bdd.repository.BDDGitSettingRepository;
import soselab.mpg.bdd.repository.FeatureRepository;
import soselab.mpg.bdd.repository.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BDDServiceImpl implements BDDService {

    private final BDDClient bddClient;

    private final ScenarioRepository scenarioRepository;
    private final FeatureRepository featureRepository;
    private final BDDGitSettingRepository bddGitSettingRepository;

    @Autowired
    public BDDServiceImpl(BDDClient bddClient, ScenarioRepository scenarioRepository,
                          FeatureRepository featureRepository, BDDGitSettingRepository bddGitSettingRepository) {
        this.bddClient = bddClient;
        this.scenarioRepository = scenarioRepository;
        this.featureRepository = featureRepository;
        this.bddGitSettingRepository = bddGitSettingRepository;
    }

    @Override
    public void parseProject() throws NoBDDProjectGitSettingException {
        List<BDDGitSetting> bddGitSettings = bddGitSettingRepository.findAll();
        if (bddGitSettings.isEmpty()) {
            throw new NoBDDProjectGitSettingException();
        }

        LatestCommitStatusDTO pull = bddClient.pull();
        if (bddGitSettings.get(0).getCommitId().equals(pull.getId())) {
            //check commit id is the same no need to update
            return;
        }

        scenarioRepository.deleteAll();
        featureRepository.deleteAll();

        List<FeatureDTO> parseData = bddClient.getParseData();
        List<Feature> features = new ArrayList<>();
        List<Scenario> scenarioList = parseData.stream().flatMap(featureDTO -> {

            Feature feature = new Feature(featureDTO.getFeature(), featureDTO.getContent());
            features.add(feature);
            List<Scenario> scenarios = featureDTO.getScenario().stream()
                    .map(scenarioBean ->
                            new Scenario(scenarioBean.getName(), scenarioBean.getLine(),
                                    scenarioBean.getTags(), feature))
                    .collect(Collectors.toList());

            return scenarios.stream();
        }).collect(Collectors.toList());

        featureRepository.save(features);
        scenarioRepository.save(scenarioList);
    }

    @Override
    public void updateGitUrl(String url) {
        bddGitSettingRepository.deleteAll();
        LatestCommitStatusDTO latestCommitStatusDTO = bddClient.gitClone(url);
        bddGitSettingRepository.save(new BDDGitSetting(url, latestCommitStatusDTO.getId(),
                latestCommitStatusDTO.getMsg()));
    }

}
