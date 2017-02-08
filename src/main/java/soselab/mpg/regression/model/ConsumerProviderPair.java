package soselab.mpg.regression.model;


public class ConsumerProviderPair {
    private String provider;
    private String consumer;

    public ConsumerProviderPair(String provider, String consumer) {
        this.provider = provider;
        this.consumer = consumer;
    }

    public String getProvider() {
        return provider;
    }

    public String getConsumer() {
        return consumer;
    }

    @Override
    public String toString() {
        return "ConsumerProviderPair{" +
                "provider='" + provider + '\'' +
                ", consumer='" + consumer + '\'' +
                '}';
    }
}
