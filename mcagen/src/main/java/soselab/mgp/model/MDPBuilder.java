package soselab.mgp.model;

import java.util.Date;
import java.util.List;

public class MDPBuilder {
    private long timestamp = new Date().getTime();
    private List<ServiceCall> serviceCall;
    private String name;
    private String swagger;
    private List<Endpoint> endpoint;
    private List<EndpointDep> endpointDep;

    public MDPBuilder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MDPBuilder setServiceCall(List<ServiceCall> serviceCall) {
        this.serviceCall = serviceCall;
        return this;
    }

    public MDPBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MDPBuilder setSwagger(String swagger) {
        this.swagger = swagger;
        return this;
    }

    public MDPBuilder setEndpoint(List<Endpoint> endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public MDPBuilder setEndpointDep(List<EndpointDep> endpointDep) {
        this.endpointDep = endpointDep;
        return this;
    }

    public MDP createMDP() {
        return new MDP(timestamp, serviceCall, name, swagger, endpoint, endpointDep);
    }
}