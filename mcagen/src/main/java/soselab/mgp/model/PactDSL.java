package soselab.mgp.model;

import java.util.List;

/**
 * Created by bernie on 2017/4/16.
 */
public class PactDSL {
    public PactDSL(String provider, String consumer) {
        this.provider = new ProviderBean();
        this.provider.setName(provider);
        this.consumer = new ConsumerBean();
        this.consumer.setName(consumer);
    }

    /**
     * provider : {"name":"theater"}
     * consumer : {"name":"order"}
     * interactions : []
     * metadata : {}
     */

    private ProviderBean provider;
    private ConsumerBean consumer;
    private MetadataBean metadata;
    private List<?> interactions;

    public ProviderBean getProvider() {
        return provider;
    }

    public void setProvider(ProviderBean provider) {
        this.provider = provider;
    }

    public ConsumerBean getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerBean consumer) {
        this.consumer = consumer;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public List<?> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<?> interactions) {
        this.interactions = interactions;
    }

    public static class ProviderBean {
        /**
         * name : theater
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ConsumerBean {
        /**
         * name : order
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MetadataBean {
    }
}
