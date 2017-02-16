package soselab.mpg.bdd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.bdd.client.BDDClient;
import soselab.mpg.bdd.client.dto.FeatureDTO;
import soselab.mpg.bdd.client.dto.LatestCommitStatusDTO;
import soselab.mpg.bdd.controller.FeatureDocumentDTO;
import soselab.mpg.bdd.model.BDDGitSetting;
import soselab.mpg.bdd.model.Feature;
import soselab.mpg.bdd.model.Scenario;
import soselab.mpg.bdd.repository.BDDGitSettingRepository;
import soselab.mpg.bdd.repository.FeatureRepository;
import soselab.mpg.bdd.repository.ScenarioRepository;
import soselab.mpg.graph.controller.dto.FeatureItem;
import soselab.mpg.graph.controller.dto.ScenarioInformationDTO;
import soselab.mpg.graph.controller.dto.ScenarioItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BDDServiceImpl implements BDDService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BDDServiceImpl.class);
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
    public boolean updateProject() throws NoBDDProjectGitSettingException {
        LOGGER.info("updateProject");
        List<BDDGitSetting> bddGitSettings = bddGitSettingRepository.findAll();
        if (bddGitSettings.isEmpty()) {
            LOGGER.info("NoBDDProjectGitSettingException");
            throw new NoBDDProjectGitSettingException();
        }

        LatestCommitStatusDTO pull = bddClient.pull();
        LOGGER.debug("setting{} pull{}", bddGitSettings, pull);
        BDDGitSetting bddGitSetting = bddGitSettings.get(0);
        if (bddGitSetting.getCommitId().equals(pull.getId())) {
            LOGGER.info("no need to update bdd");
            //check commit id is the same no need to update
            return false;
        }

        scenarioRepository.deleteAll();
        featureRepository.deleteAll();

        List<FeatureDTO> parseData = bddClient.getParseData();
        LOGGER.debug("parse data {}", parseData);

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
        LOGGER.debug("features {}", features);
        LOGGER.debug("scenarioList {}", scenarioList);

        featureRepository.save(features);
        scenarioRepository.save(scenarioList);

        // update commit information
        bddGitSetting.setCommitId(pull.getId());
        bddGitSetting.setCommitMessage(pull.getMsg());
        bddGitSettingRepository.save(bddGitSetting);
        return true;
    }

    /**
     * @param url git url
     * @return true means update success. false means no need to update
     */
    @Override
    public boolean updateGitUrl(String url) {
        if (url == null || url.equals("") || getGitUrl().equals(url)) return false;
        bddGitSettingRepository.deleteAll();
        LatestCommitStatusDTO latestCommitStatusDTO = bddClient.gitClone(url);
        bddGitSettingRepository.save(new BDDGitSetting(url, "", ""));
        return true;
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

    @Override
    public ScenarioInformationDTO getScenarioInfomation() {
        List<Scenario> scenarios = scenarioRepository.findAll();
        List<Feature> features = featureRepository.findAll();

        LOGGER.debug("all scenarios {}, features {}", scenarios, features);
        List<ScenarioItem> scenarioItems = scenarios.stream()
                .map(scenario -> {
                    return new ScenarioItem(scenario.getId().toHexString(), scenario.getName(), scenario.getContent(),
                            scenario.getFeature().getId().toHexString());
                })
                .collect(Collectors.toList());

        List<FeatureItem> featureItems = features.stream()
                .map(feature -> new FeatureItem(feature.getId().toHexString(), feature.getName(), feature.getContent()))
                .collect(Collectors.toList());

        LOGGER.info("scenarioItems {}, featuresItems {}", scenarioItems, featureItems);

        return new ScenarioInformationDTO(scenarioItems, featureItems);
    }

    @Override
    public List<String> getAllTagsRelateToService(String serviceName) {
        List<Scenario> all = scenarioRepository.findAll();
        return all.stream()
                .flatMap(scenario -> scenario.getTags().stream())
                .filter(tag -> tag.contains(serviceName))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<FeatureDocumentDTO> getFeatures() {
        List<Feature> all = featureRepository.findAll();
        if (all.isEmpty())
            return Collections.emptyList();
        return all.stream()
                .map(feature -> new FeatureDocumentDTO(feature.getName(), feature.getContent()))
                .collect(Collectors.toList());
    }

}
