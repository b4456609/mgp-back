package soselab.mpg.regression.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soselab.mpg.mpd.model.IDExtractor;
import soselab.mpg.mpd.service.EndpointAnnotationBuilder;
import soselab.mpg.regression.controller.RegressionController;
import soselab.mpg.regression.model.AnnotationWithOrder;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegressionPicker {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegressionPicker.class);

    public List<ConsumerProviderPair> getRegressionServiceTestPair(List<List<String>> paths, String target) {
        List<List<String>> targetEndpoints = getTargetEndpoints(paths, target);
        HashMap<String, Integer> serviceAndPriority = new HashMap<>();
        targetEndpoints.forEach(path -> {
            //get index of target service
            int targetIndex = getTargetIndex(target, path);

            //generate provider and consumer pair
            for (int i = 1; i < path.size(); i++) {
                String consumerServiceName = IDExtractor.getServiceName(path.get(i - 1));
                String providerServiceName = IDExtractor.getServiceName(path.get(i));

                final int priority = targetIndex - i;
                // if order are smaller replace it in hashmap
                serviceAndPriority.compute(consumerServiceName + "!" + providerServiceName,
                        (k, v) -> v == null ? priority : Math.min(priority, v));

            }
        });

        List<ConsumerProviderPair> result = new ArrayList<>();
        serviceAndPriority.forEach((k, v) -> {
            String[] split = k.split("!");
            String consumer = split[0];
            String provider = split[1];
            result.add(new ConsumerProviderPair(provider, consumer, v));
        });
        Collections.sort(result, Comparator.comparingInt(ConsumerProviderPair::getOrder));
        LOGGER.info("service Test regression result: {}", result);
        return result;
    }

    private int getTargetIndex(String target, List<String> path) {
        int targetIndex = -1;
        for (int i = path.size() - 1; i >= 0; i--) {
            if (IDExtractor.getServiceName(path.get(i)).equals(target)) {
                targetIndex = i;
                break;
            }
        }
        return targetIndex;
    }

    public List<String> getScenarioAnnotations(List<List<String>> paths, String target) {
        List<List<String>> targetEndpoints = getTargetEndpoints(paths, target);
        Map<String, AnnotationWithOrder> endpointAndAnnotation = new HashMap<>();
        targetEndpoints.forEach(path -> {
            int targetIndex = getTargetIndex(target, path);
            for (int i = 0; i < path.size(); i++) {
                AnnotationWithOrder annotationWithOrder = endpointAndAnnotation.get(path.get(i));
                if (annotationWithOrder == null || annotationWithOrder.getOrder() > targetIndex - i) {
                    endpointAndAnnotation.put(path.get(i),
                            new AnnotationWithOrder(targetIndex - i, EndpointAnnotationBuilder.build(path.get(i))));
                }
            }
        });
        return endpointAndAnnotation.values().stream()
                .sorted(Comparator.comparingInt(AnnotationWithOrder::getOrder))
                .map(i -> i.getId())
                .collect(Collectors.toList());
    }

    private List<List<String>> getTargetEndpoints(List<List<String>> paths, String target) {
        return paths
                .stream()
                .map(path -> {
                    int lastIndex = -1;
                    for (int i = 0; i < path.size(); i++) {
                        String serviceName = IDExtractor.getServiceName(path.get(i));
                        if (serviceName.contains(target)) {
                            lastIndex = i;
                        }
                    }
                    if (lastIndex == -1)
                        return new ArrayList<String>();
                    if (lastIndex == path.size() - 1)
                        return path;
                    if (lastIndex == path.size() - 2)
                        return path;
                    return path.subList(0, lastIndex + 2);
                })
                .collect(Collectors.toList());
    }
}
