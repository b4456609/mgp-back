package soselab.mpg.mpd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ServiceName {
    @Id
    private String id;

    @Indexed(unique = true)
    private String serviceName;

    public ServiceName() {
    }

    public ServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
