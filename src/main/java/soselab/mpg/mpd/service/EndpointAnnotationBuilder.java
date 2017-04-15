package soselab.mpg.mpd.service;

public class EndpointAnnotationBuilder {
    public static String build(String id) {
        return "@" + id.replace(' ', '_');
    }
}
