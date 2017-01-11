package soselab.mpg.mpd.model;


import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Mdp {

    @Id
    private String id;

    @NotNull
    private long timestamp;
    @NotNull
    private List<ServiceCall> serviceCall;
    @NotNull
    private String name;
    @NotNull
    private String swagger;
    @NotNull
    private List<Endpoint> endpoint;
    @NotNull
    private List<EndpointDep> endpointDep;

    public Mdp() {
    }

    public Mdp(Long timestamp, List<ServiceCall> serviceCall, String name, String swagger, List<Endpoint> endpoint, List<EndpointDep> endpointDep) {
        this.timestamp = timestamp;
        this.serviceCall = serviceCall;
        this.name = name;
        this.swagger = swagger;
        this.endpoint = endpoint;
        this.endpointDep = endpointDep;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Mdp{" +
                "timestamp=" + timestamp +
                ", serviceCall=" + serviceCall +
                ", name='" + name + '\'' +
                ", swagger='" + swagger + '\'' +
                ", endpoint=" + endpoint +
                ", endpointDep=" + endpointDep +
                '}';
    }
}
