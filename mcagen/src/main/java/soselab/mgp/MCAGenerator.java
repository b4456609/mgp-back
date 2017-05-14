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
import java.util.*;
import java.util.stream.Collectors;

public class MCAGenerator {
    /*public static final int[] ENDPOINT_COUNT_DATA = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8,
            8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10,
            10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13,
            13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
            16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 17, 17, 17, 17, 18, 18, 18, 18, 18, 18, 18, 18, 19, 19, 19, 19,
            20, 20, 20, 20, 20, 20, 21, 21, 21, 21, 22, 22, 23, 23, 23, 23, 23, 24, 24, 24, 24, 24, 24, 25, 25, 25,
            25, 25, 26, 26, 27, 27, 27, 28, 28, 29, 29, 29, 29, 29, 29, 30, 30, 30, 30, 30, 30, 31, 32, 32, 32, 33,
            33, 34, 34, 34, 34, 34, 35, 35, 35, 35, 37, 38, 38, 38, 39, 40, 41, 43, 44, 46, 46, 46, 47, 47, 47, 47,
            50, 51, 51, 52, 53, 53, 55, 56, 57, 58, 63, 63, 68, 79, 90, 98, 110, 111, 111, 114, 116, 124, 127, 134,
            157, 166, 213, 217, 228, 264, 267, 277};*/
    public static final int[] ENDPOINT_COUNT_DATA = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

    public static final Random RANDOM = new Random();
    public static final String[] httpMethods = {"POST", "GET", "DELETE", "PUT"};
    public static final int SERVICE_NUMBER = 20;
    //    public static final int ENDPOINT_MAX_NUMBER = 5;
    public static final int SERVICE_CALL_MAX_NUMBER = 5;
//    public static final int SERVICE_DEPENDENCY_MAX_NUMBER = 2;

    public static final int MAX_SCENARIO_NUM = 5;
    public static final int SCENARIO_NUMBER = 10;

    public static void main(String[] args) {
        List<Service> services = new ArrayList<>();

        //generate service
        for (int i = 0; i < SERVICE_NUMBER; i++) {
            String serviceName = String.format("S%03d", i + 1);
            List<Endpoint> serviceEndpoints = randomEndpoints(serviceName);
            Service service = new Service(serviceName, serviceEndpoints);
            services.add(service);
        }

        //generate service call
        List<MDP> collect = services.stream()
                .map(service -> {
                    List<ServiceCall> serviceCalls = randomServiceEndpoints(services);
                    System.out.println(serviceCalls.size());
                    List<EndpointDep> endpointDeps = randomPickServiceDep(service.getServiceEndpoints(), serviceCalls);
//                    System.out.println(serviceCalls);
                    //generate service test dsl
                    endpointDeps.forEach(endpointDep -> PactGenerator.build(endpointDep.getTo().split(" ")[0], endpointDep.getFrom().split(" ")[0]));
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

        Map<String, Set<String>> stringSetMap = genScenario(collect);
        ScenarioWriter.write(stringSetMap);

        System.out.println("=============================================================service");
        int asInt = collect.stream().mapToInt(i -> i.getServiceCall().size()).reduce((all, age) -> all + age).getAsInt();
        System.out.println("service Call num:"  + asInt);
        System.out.println("=============================================================scenario");
        System.out.println(stringSetMap.size());
        stringSetMap.forEach(((s, strings) -> {
            System.out.println(s + ", "+ strings.size()+ ", "+ strings);
        }));
        System.out.println(stringSetMap);
    }

    public static Map<String, Set<String>> genScenario(List<MDP> mpd) {
        Map<String, Set<String>> result = new HashMap<>();
        for (int i = 0; i < SCENARIO_NUMBER; i++) {
            String scenarioName = String.format("Scenario%02d", i + 1);
            int serviceCallNum = RANDOM.nextInt(MAX_SCENARIO_NUM) + 1;
            Set<String> anntations = new HashSet<>();
            Collections.shuffle(mpd);
            Set<String > endpoints = mpd.stream().limit(serviceCallNum)
                    .map(j -> j.getEndpoint().get(RANDOM.nextInt(j.getEndpoint().size())).getId())
                    .map(st -> "@" + st.replace(" ", "_"))
                    .collect(Collectors.toSet());
            result.put(scenarioName, endpoints);
        }
        return result;
    }

    public static List<EndpointDep> randomPickServiceDep(List<Endpoint> serviceEndpoints,
                                                         List<ServiceCall> serviceCalls) {
        return serviceCalls.stream()
                .flatMap(serviceCall -> {
                    String id = serviceCall.getId();
                    ArrayList<Endpoint> endpoints = new ArrayList<>(serviceEndpoints);
                    Collections.shuffle(endpoints);
                    return endpoints.stream()
                            .limit(1)
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

    public static int getServiceCallNum() {
        return (int) Math.floor(Math.min(RANDOM.nextGaussian() * 2, 2));
    }

    public static List<Endpoint> randomEndpoints(String serviceName) {
        List<Endpoint> serviceEndpoints = new ArrayList<>();
//        int num = RANDOM.nextInt(ENDPOINT_MAX_NUMBER - 1) + 1;
        int index = RANDOM.nextInt(ENDPOINT_COUNT_DATA.length);
        int dataNum = ENDPOINT_COUNT_DATA[index];
        // divide into 19 partition
        int partition = dataNum / 19;
        // each partition has 15 endpoint
        int num = RANDOM.nextInt(5) + 1;

        for (int i = 0; i < num; i++) {
            String path = "/path" + i;
            String httpMethod = httpMethods[RANDOM.nextInt(httpMethods.length)];
            serviceEndpoints.add(new Endpoint(String.format("%s %s %s %s", serviceName,
                    "endpoint", path, httpMethod), path, httpMethod));
        }
        return serviceEndpoints;
    }
}
