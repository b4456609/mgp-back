package soselab.mpg.pact.model;


import org.springframework.data.annotation.Id;

public class ServiceCallRelationInformation {
    @Id
    private String id;
    private String provider;
    private String consumer;
    private String pact;
    private String version;

    public ServiceCallRelationInformation() {
    }

    public ServiceCallRelationInformation(String provider, String consumer, String pact, String version) {
        this.provider = provider;
        this.consumer = consumer;
        this.pact = pact;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getPact() {
        return pact;
    }

    public void setPact(String pact) {
        this.pact = pact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ServiceCallRelationInformation{" +
                "provider='" + provider + '\'' +
                ", consumer='" + consumer + '\'' +
                ", pact='" + pact + '\'' +
                '}';
    }
}
