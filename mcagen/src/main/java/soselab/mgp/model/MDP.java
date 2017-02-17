package soselab.mgp.model;


import java.util.List;

public class MDP {
    private long timestamp;

    private List<ServiceCall> serviceCall;

    private String name;

    private String swagger;

    private List<Endpoint> endpoint;

    private List<EndpointDep> endpointDep;

    public MDP(long timestamp, List<ServiceCall> serviceCall, String name, String swagger, List<Endpoint> endpoint, List<EndpointDep> endpointDep) {
        this.timestamp = timestamp;
        this.serviceCall = serviceCall;
        this.name = name;
        this.swagger = swagger;
        this.endpoint = endpoint;
        this.endpointDep = endpointDep;
    }

    public long getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (long timestamp)
    {
        this.timestamp = timestamp;
    }

    public List<ServiceCall> getServiceCall ()
    {
        return serviceCall;
    }

    public void setServiceCall (List<ServiceCall> serviceCall)
    {
        this.serviceCall = serviceCall;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSwagger ()
    {
        return swagger;
    }

    public void setSwagger (String swagger)
    {
        this.swagger = swagger;
    }

    public List<Endpoint> getEndpoint ()
    {
        return endpoint;
    }

    public void setEndpoint (List<Endpoint> endpoint)
    {
        this.endpoint = endpoint;
    }

    public List<EndpointDep> getEndpointDep ()
    {
        return endpointDep;
    }

    public void setEndpointDep (List<EndpointDep> endpointDep)
    {
        this.endpointDep = endpointDep;
    }

    @Override
    public String toString() {
        return "MDP{" +
                "timestamp=" + timestamp +
                ", serviceCall=" + serviceCall +
                ", name='" + name + '\'' +
                ", swagger='" + swagger + '\'' +
                ", endpoint=" + endpoint +
                ", endpointDep=" + endpointDep +
                '}';
    }
}
