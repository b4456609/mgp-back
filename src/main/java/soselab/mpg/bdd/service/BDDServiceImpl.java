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
import java.util.Set;
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
        if (url == null)
            return;
        bddGitSettingRepository.deleteAll();

        if (url.equals("")) return;

        LatestCommitStatusDTO latestCommitStatusDTO = bddClient.gitClone(url);
        bddGitSettingRepository.save(new BDDGitSetting(url, latestCommitStatusDTO.getId(),
                latestCommitStatusDTO.getMsg()));
    }

    @Override
    public String getGitUrl() {
        List<BDDGitSetting> settings = bddGitSettingRepository.findAll();
        if (settings.isEmpty())
            return "";
        return settings.get(0).getUrl();
    }

    @Override
    public List<ScenarioWithTagDTO> getScenarioWithTag() {
        List<Scenario> all = scenarioRepository.findAll();
        List<ScenarioWithTagDTO> collect = all.stream()
                .map(scenario -> {
                    Set<String> tags = scenario.getTags().stream()
                            //remove @ in tag string
                            .map(tag -> {
                                return tag.substring(1);
                            })
                            .collect(Collectors.toSet());
                    return new ScenarioWithTagDTO(scenario.getId().toHexString(), scenario.getName(), tags);
                })
                .collect(Collectors.toList());
        return collect;
    }

}
