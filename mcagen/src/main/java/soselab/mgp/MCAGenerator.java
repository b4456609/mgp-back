package soselab.mgp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import soselab.mgp.model.Endpoint;
import soselab.mgp.model.EndpointDep;
import soselab.mgp.model.MDP;
import soselab.mgp.model.ServiceCall;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MCAGenerator {
    public static final Random RANDOM = new Random();
    public static final String[] httpMethods = {"POST", "GET", "DELETE", "PUT"};
    public static final int SERVICE_NUMBER = 5;
    public static final int ENDPOINT_MAX_NUMBER = 5;
    public static final int SERVICE_CALL_MAX_NUMBER = 5;
    public static final int SERVICE_DEPENDENCY_MAX_NUMBER = 2;

    public static void main(String[] args) {
        List<Service> services = new ArrayList<>();

        //generate service
        for (int i = 0; i < SERVICE_NUMBER; i++) {
            String serviceName = "S" + i;
            List<Endpoint> serviceEndpoints = randomEndpoints(serviceName);
            Service service = new Service(serviceName, serviceEndpoints);
            services.add(service);
        }

        //generate service call
        List<MDP> collect = services.stream()
                .map(service -> {
                    List<ServiceCall> serviceCalls = randomServiceEndpoints(services);
                    List<EndpointDep> endpointDeps = randomPickServiceDep(service.getServiceEndpoints(), serviceCalls);
                    System.out.println(serviceCalls);
                    return new MDP(0, serviceCalls, service.getName(), "",
                            service.getServiceEndpoints(), endpointDeps);
                })
                .collect(Collectors.toList());
        collect.forEach(i -> {
            try {
                Path path = Paths.get("build/output/" + i.getName() + ".json");
                Files.createDirectories(path.getParent());
                Files.write(path,
                        Collections.singletonList(JSON.toJSONString(i, SerializerFeature.PrettyFormat)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static List<EndpointDep> randomPickServiceDep(List<Endpoint> serviceEndpoints,
                                                         List<ServiceCall> serviceCalls) {
        return serviceCalls.stream()
                .flatMap(serviceCall -> {
                    String id = serviceCall.getId();
                    return serviceEndpoints.stream()
                            .filter(i -> RANDOM.nextBoolean())
                            .limit(RANDOM.nextInt(SERVICE_DEPENDENCY_MAX_NUMBER - 1) + 1)
                            .map(i -> new EndpointDep(i.getId(), id));

                })
                .collect(Collectors.toList());
    }

    public static List<ServiceCall> randomServiceEndpoints(List<Service> services) {
        List<Endpoint> collect = services.stream()
                .flatMap(i -> i.getServiceEndpoints().stream())
                .collect(Collectors.toList());
        Collections.shuffle(collect);
        return collect.stream()
                .limit(RANDOM.nextInt(SERVICE_CALL_MAX_NUMBER))
                .map(i -> new ServiceCall(i.getId().replaceAll("endpoint", "serviceCall"),
                        i.getPath(),
                        i.getMethod(),
                        i.getId().split(" ")[1],
                        RANDOM.nextBoolean()))
                .collect(Collectors.toList());
    }

    public static List<Endpoint> randomEndpoints(String serviceName) {
        List<Endpoint> serviceEndpoints = new ArrayList<>();
        int num = RANDOM.nextInt(ENDPOINT_MAX_NUMBER - 1) + 1;
        for (int i = 0; i < num; i++) {
            String path = "/path" + i;
            String httpMethod = httpMethods[RANDOM.nextInt(httpMethods.length)];
            serviceEndpoints.add(new Endpoint(String.format("%s %s %s %s", serviceName,
                    "endpoint", path, httpMethod), path, httpMethod));
        }
        return serviceEndpoints;
    }
}
