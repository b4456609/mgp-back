package soselab.mpg.mpd.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ServiceName {
    @Indexed(unique = true)
    String serviceName;

    public ServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "ServiceName{" +
                "serviceName='" + serviceName + '\'' +
                '}';
    }
}
