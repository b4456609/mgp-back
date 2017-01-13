package soselab.mpg.model;

import org.springframework.data.annotation.Id;

/**
 * Created by bernie on 2017/1/12.
 */
public class ServiceName {
    @Id
    String id;
    String serviceName;

    public ServiceName(String id, String serviceName) {
        this.id = id;
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
