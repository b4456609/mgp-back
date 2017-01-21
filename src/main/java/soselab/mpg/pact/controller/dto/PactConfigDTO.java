package soselab.mpg.pact.controller.dto;

/**
 * Created by bernie on 2017/1/21.
 */
public class PactConfigDTO {
    private String url;

    public PactConfigDTO(String url) {
        this.url = url;
    }

    public PactConfigDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PactConfigDTO{" +
                "url='" + url + '\'' +
                '}';
    }
}
