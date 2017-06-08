package soselab.mpg.regression.controller.dto;

import io.swagger.annotations.ApiModelProperty;

public class ConsumerDetail {

    @ApiModelProperty(value = "Consumer name")
    private String consumer;

    @ApiModelProperty(value = "Pact file url from pact broker")
    private String pactFile;

    public ConsumerDetail(String consumer, String pactFile) {
        this.consumer = consumer;
        this.pactFile = pactFile;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getPactFile() {
        return pactFile;
    }

    public void setPactFile(String pactFile) {
        this.pactFile = pactFile;
    }

    @Override
    public String toString() {
        return "ConsumerDetail{" +
                "consumer='" + consumer + '\'' +
                ", pactFile='" + pactFile + '\'' +
                '}';
    }
}
