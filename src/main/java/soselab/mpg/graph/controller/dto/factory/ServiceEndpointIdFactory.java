package soselab.mpg.graph.controller.dto.factory;

/**
 * Created by bernie on 1/13/17.
 */
public class ServiceEndpointIdFactory {
    public static String getId(String serviceName, String path, String method) {
        return String.format("%s endpoint %s %s", serviceName, path, method);
    }
}
