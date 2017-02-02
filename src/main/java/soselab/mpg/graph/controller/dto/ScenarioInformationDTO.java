package soselab.mpg.graph.controller.dto;

import java.util.List;

public class ScenarioInformationDTO {
    List<ScenarioItem> scenarios;
    List<FeatureItem> features;

    public ScenarioInformationDTO(List<ScenarioItem> scenarios, List<FeatureItem> features) {
        this.scenarios = scenarios;
        this.features = features;
    }

    public List<ScenarioItem> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioItem> scenarios) {
        this.scenarios = scenarios;
    }

    public List<FeatureItem> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureItem> features) {
        this.features = features;
    }
}
