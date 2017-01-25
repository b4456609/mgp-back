package soselab.mpg.graph.controller.dto;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ServiceCallInformationDTO {
    private String consumer;
    private String provider;
    private String pact;

    public ServiceCallInformationDTO() {
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPact() {
        return pact;
    }

    public void setPact(String pact) {
        this.pact = pact;
    }

    @Override
    public String toString() {
        return "ServiceCallInformationDTO{" +
                "consumer='" + consumer + '\'' +
                ", provider='" + provider + '\'' +
                ", pact='" + pact + '\'' +
                '}';
    }
}
