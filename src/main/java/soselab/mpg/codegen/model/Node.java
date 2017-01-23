package soselab.mpg.codegen.model;

public class Node {

    private String request;
    private String unirest;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getUnirest() {
        return unirest;
    }

    public void setUnirest(String unirest) {
        this.unirest = unirest;
    }

    @Override
    public String toString() {
        return "Node{" +
                "request='" + request + '\'' +
                ", unirest='" + unirest + '\'' +
                '}';
    }
}
