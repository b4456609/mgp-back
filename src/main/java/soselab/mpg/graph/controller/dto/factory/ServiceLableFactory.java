package soselab.mpg.graph.controller.dto.factory;

/**
 * Created by bernie on 2017/1/12.
 */
public class ServiceLableFactory {
    public static String createEndpointLabel(String path, String method) {
        return String.format("%s %s", path, method);
    }

    public static String createServiceLabel(String serviceName) {
        return serviceName;
    }
}
