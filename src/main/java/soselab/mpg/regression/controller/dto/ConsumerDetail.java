package soselab.mpg.regression.controller.dto;

public class ConsumerDetail {
    private String consumer;
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
