package soselab.mpg.graph.model;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class EndpointQuery {
    private String endpointId;
    private String path;
    private String httpMethod;

    public EndpointQuery() {
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return "EndpointQuery{" +
                "endpointId='" + endpointId + '\'' +
                ", path='" + path + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
