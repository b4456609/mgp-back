package soselab.mpg.mpd.model;

public class Endpoint {
    private String id;

    private String path;

    private String method;

    public Endpoint() {
    }

    public Endpoint(String id, String path, String method) {
        this.id = id;
        this.path = path;
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Endpoint [id = " + id + ", path = " + path + ", method = " + method + "]";
    }
}
