package soselab.mpg.graph.controller.dto;

import soselab.mpg.codegen.model.CodeSnippet;

/**
 * Created by bernie on 2017/1/24.
 */
public class EndpointInformationDTO {
    private String id;
    private String httpMethod;
    private String path;
    private CodeSnippet code;

    public EndpointInformationDTO(String id, String httpMethod, String path, CodeSnippet code) {
        this.id = id;
        this.httpMethod = httpMethod;
        this.path = path;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public CodeSnippet getCode() {
        return code;
    }
}
