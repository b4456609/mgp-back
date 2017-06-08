package soselab.mpg.regression.controller.dto;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PactTestCaseDTO {
    @ApiModelProperty(value = "Provider name", example = "user")
    private String provider;
    @ApiModelProperty(value = "Provider host environment variable name", example = "USER_HOST")
    private String host;
    @ApiModelProperty(value = "Provider port environment variable name", example = "USER_PORT")
    private String port;
    @ApiModelProperty(value = "Consumer Information")
    private List<ConsumerDetail> hasPactWith;

    public PactTestCaseDTO(String provider, List<ConsumerDetail> hasPactWith) {
        this.provider = provider;
        this.host = provider.toUpperCase() + "_HOST";
        this.port = provider.toUpperCase() + "_PORT";
        this.hasPactWith = hasPactWith;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<ConsumerDetail> getHasPactWith() {
        return hasPactWith;
    }

    public void setHasPactWith(List<ConsumerDetail> hasPactWith) {
        this.hasPactWith = hasPactWith;
    }
}
