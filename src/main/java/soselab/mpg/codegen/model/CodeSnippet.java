package soselab.mpg.codegen.model;

public class CodeSnippet {
    private String java;
    private String curl;
    private String node;

    public String getJava() {
        return java;
    }

    public void setJava(String java) {
        this.java = java;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "java=" + java +
                ", curl='" + curl + '\'' +
                ", node=" + node +
                '}';
    }
}
