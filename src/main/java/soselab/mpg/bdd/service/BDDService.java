package soselab.mpg.bdd.service;

import soselab.mpg.bdd.controller.FeatureDocumentDTO;
import soselab.mpg.graph.controller.dto.ScenarioInformationDTO;

import java.util.List;

/**
 * Created by bernie on 2017/1/29.
 */
public interface BDDService {
    boolean updateProject() throws NoBDDProjectGitSettingException;

    boolean updateGitUrl(String url);

    String getGitUrl();

    List<ScenarioWithTagDTO> getScenarioWithTag();

    ScenarioInformationDTO getScenarioInfomation();

    List<String> getAllTagsRelateToService(String serviceName);

    List<FeatureDocumentDTO> getFeatures();

    List<String> getTag(List<String> scenarioAnnotations, String serviceName);
}
