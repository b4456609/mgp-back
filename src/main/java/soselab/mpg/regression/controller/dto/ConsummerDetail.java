package soselab.mpg.regression.controller.dto;

public class ConsummerDetail {
    private String consumer;
    private String pactFile;

    public ConsummerDetail(String consumer, String pactFile) {
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
        return "ConsummerDetail{" +
                "consumer='" + consumer + '\'' +
                ", pactFile='" + pactFile + '\'' +
                '}';
    }
}
