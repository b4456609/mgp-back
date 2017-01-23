package soselab.mpg.codegen.model;

public class Java {

    private String okhttp;
    private String unirest;

    public String getOkhttp() {
        return okhttp;
    }

    public void setOkhttp(String okhttp) {
        this.okhttp = okhttp;
    }

    public String getUnirest() {
        return unirest;
    }

    public void setUnirest(String unirest) {
        this.unirest = unirest;
    }

    @Override
    public String toString() {
        return "Java{" +
                "okhttp='" + okhttp + '\'' +
                ", unirest='" + unirest + '\'' +
                '}';
    }
}