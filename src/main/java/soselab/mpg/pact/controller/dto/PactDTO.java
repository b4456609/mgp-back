package soselab.mpg.pact.controller.dto;

/**
 * Created by bernie on 2017/1/22.
 */
public class PactDTO {
    private String consumer;
    private String provider;
    private String pact;

    public PactDTO() {
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
        return "PactDTO{" +
                "consumer='" + consumer + '\'' +
                ", provider='" + provider + '\'' +
                ", pact='" + pact + '\'' +
                '}';
    }
}
