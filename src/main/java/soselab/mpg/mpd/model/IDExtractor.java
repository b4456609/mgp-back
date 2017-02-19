package soselab.mpg.mpd.model;


public class IDExtractor {
    public static String getServiceName(String id) {
        String[] serviceCallSplit = id.split(" ");
        return serviceCallSplit[0];
    }

    public static String getPath(String dep) {
        String[] serviceCallSplit = dep.split(" ");
        return serviceCallSplit[2];
    }

    public static String getHttpMethod(String dep) {
        String[] serviceCallSplit = dep.split(" ");
        return serviceCallSplit[3];
    }
}
