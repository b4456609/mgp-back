package soselab.mpg.bdd.client.dto;

import java.util.List;

public class FeatureDTO {
    private String feature;
    private String content;
    private List<ScenarioBean> scenario;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ScenarioBean> getScenario() {
        return scenario;
    }

    public void setScenario(List<ScenarioBean> scenario) {
        this.scenario = scenario;
    }

    @Override
    public String toString() {
        return "FeatureDTO{" +
                "feature='" + feature + '\'' +
                ", content='" + content + '\'' +
                ", scenario=" + scenario +
                '}';
    }

    public static class ScenarioBean {

        private String name;
        private String line;
        private List<String> tags;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public String toString() {
            return "ScenarioBean{" +
                    "name='" + name + '\'' +
                    ", line='" + line + '\'' +
                    ", tags=" + tags +
                    '}';
        }
    }
}
