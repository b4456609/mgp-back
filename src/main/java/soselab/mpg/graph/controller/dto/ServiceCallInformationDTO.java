package soselab.mpg.graph.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ServiceCallInformationDTO {
    @ApiModelProperty("Consumer id")
    private String consumer;
    @ApiModelProperty("provider id")
    private String provider;
    @ApiModelProperty("Pact DSL json content")
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
