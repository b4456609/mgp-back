package soselab.mpg.bdd.service;

import java.util.Set;

public class ScenarioWithTagDTO {
    private String id;
    private String name;
    private Set<String> tags;

    public ScenarioWithTagDTO(String id, String name, Set<String> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
