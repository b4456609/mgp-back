package soselab.mpg.codegen.model;

public class CodeSnippet {
    private Java java;
    private String curl;
    private Boolean js;
    private Node node;

    public Java getJava() {
        return java;
    }

    public void setJava(Java java) {
        this.java = java;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public Boolean getJs() {
        return js;
    }

    public void setJs(Boolean js) {
        this.js = js;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "java=" + java +
                ", curl='" + curl + '\'' +
                ", js=" + js +
                ", node=" + node +
                '}';
    }
}
