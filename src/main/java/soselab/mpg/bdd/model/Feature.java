package soselab.mpg.bdd.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Feature {
    @Id
    private ObjectId id;
    private String name;
    private String content;

    public Feature() {
    }

    public Feature(String name, String content) {
        this.id = new ObjectId();
        this.name = name;
        this.content = content;
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
}
