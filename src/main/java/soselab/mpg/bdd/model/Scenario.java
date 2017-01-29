package soselab.mpg.bdd.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Scenario {
    @Id
    private ObjectId id;
    private String name;
    private String content;

    @Indexed
    private List<String> tags;

    @DBRef
    private Feature feature;

    public Scenario() {
    }

    public Scenario(String name, String content, List<String> tags, Feature feature) {
        this.id = new ObjectId();
        this.name = name;
        this.content = content;
        this.tags = tags;
        this.feature = feature;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
