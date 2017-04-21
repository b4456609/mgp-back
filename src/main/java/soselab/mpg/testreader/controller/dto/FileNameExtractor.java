package soselab.mpg.testreader.controller.dto;

public class FileNameExtractor {
    public static String getServiceName(String filename) {
        int serviceStartIndex = filename.indexOf("_", 1) + 1;
        int serviceEndIndex = filename.lastIndexOf(".");
        return filename.substring(serviceStartIndex, serviceEndIndex);
    }

    public static int getRunNumber(String filename) {
        int endIndex = filename.indexOf("_", 1);
        return Integer.valueOf(filename.substring(1, endIndex));
    }

    public static String getFileNameWithoutType(String filename) {
        int endIndex = filename.lastIndexOf(".");
        return filename.substring(0, endIndex);
    }
}
