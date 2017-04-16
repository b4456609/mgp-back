package soselab.mpg.regression.model;


public class ConsumerProviderPair implements PriorityOrder{
    private String provider;
    private String consumer;
    private int order;

    public ConsumerProviderPair(String provider, String consumer, int order) {
        this.provider = provider;
        this.consumer = consumer;
        this.order = order;
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
                ", order=" + order +
                '}';
    }

    @Override
    public int getOrder() {
        return order;
    }
}
