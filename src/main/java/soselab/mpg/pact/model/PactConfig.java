package soselab.mpg.pact.model;

import org.springframework.data.annotation.Id;

public class PactConfig {
    @Id
    private String id;
    private String url;

    public PactConfig() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
