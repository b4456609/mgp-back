package soselab.mpg.regression.controller.dto;


import java.util.List;

public class PactTestCaseDTO {
    private String provider;
    private String host;
    private String port;
    private List<ConsummerDetail> hasPactWith;

    public PactTestCaseDTO(String provider, List<ConsummerDetail> hasPactWith) {
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

    public List<ConsummerDetail> getHasPactWith() {
        return hasPactWith;
    }

    public void setHasPactWith(List<ConsummerDetail> hasPactWith) {
        this.hasPactWith = hasPactWith;
    }
}
