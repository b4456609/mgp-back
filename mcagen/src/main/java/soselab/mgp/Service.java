package soselab.mgp;

import soselab.mgp.model.Endpoint;

import java.util.List;

public class Service {
    private String name;
    private List<Endpoint> serviceEndpoints;

    public Service(String name, List<Endpoint> serviceEndpoints) {
        this.name = name;
        this.serviceEndpoints = serviceEndpoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Endpoint> getServiceEndpoints() {
        return serviceEndpoints;
    }

    public void setServiceEndpoints(List<Endpoint> serviceEndpoints) {
        this.serviceEndpoints = serviceEndpoints;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", serviceEndpoints=" + serviceEndpoints +
                '}';
    }
}
