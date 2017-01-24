package soselab.mpg.graph.controller.dto;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ServiceInformationDTO {
    private String id;
    private int endpointCount;
    private int serviceCallCount;
    private String swagger;

    public ServiceInformationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEndpointCount() {
        return endpointCount;
    }

    public void setEndpointCount(int endpointCount) {
        this.endpointCount = endpointCount;
    }

    public int getServiceCallCount() {
        return serviceCallCount;
    }

    public void setServiceCallCount(int serviceCallCount) {
        this.serviceCallCount = serviceCallCount;
    }

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    @Override
    public String toString() {
        return "ServiceInformationDTO{" +
                "id='" + id + '\'' +
                ", endpointCount='" + endpointCount + '\'' +
                ", serviceCallCount='" + serviceCallCount + '\'' +
                ", swagger='" + swagger + '\'' +
                '}';
    }
}
