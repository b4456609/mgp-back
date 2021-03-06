package soselab.mpg.app.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Fan on 2017/2/2.
 */
public class SettingDTO {
    @ApiModelProperty(value = "Pact url")
    private String pactHostUrl;
    @ApiModelProperty(value = "Bdd git project url in https format")
    private String bddGitUrl;

    public SettingDTO() {
    }

    public SettingDTO(String pactHostUrl, String bddGitUrl) {
        this.pactHostUrl = pactHostUrl;
        this.bddGitUrl = bddGitUrl;
    }

    public String getPactHostUrl() {
        return pactHostUrl;
    }

    public void setPactHostUrl(String pactHostUrl) {
        this.pactHostUrl = pactHostUrl;
    }

    public String getBddGitUrl() {
        return bddGitUrl;
    }

    public void setBddGitUrl(String bddGitUrl) {
        this.bddGitUrl = bddGitUrl;
    }

    @Override
    public String toString() {
        return "SettingDTO{" +
                "pactHostUrl='" + pactHostUrl + '\'' +
                ", bddGitUrl='" + bddGitUrl + '\'' +
                '}';
    }
}
