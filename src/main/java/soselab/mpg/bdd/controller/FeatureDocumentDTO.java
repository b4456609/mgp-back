package soselab.mpg.bdd.controller;


public class FeatureDocumentDTO {
    private String name;
    private String content;

    public FeatureDocumentDTO(String name, String content) {
        this.name = name;
        this.content = content;
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
