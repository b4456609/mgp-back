package soselab.mpg.regression.service;

import org.springframework.stereotype.Service;
import soselab.mpg.mpd.model.IDExtractor;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegressionPicker {
    public List<ConsumerProviderPair> getRegressionServiceTestPair(List<List<String>> paths, String target) {
        List<List<String>> targetEndpoints = getTargetEndpoints(paths, target);
        return targetEndpoints.stream()
                .flatMap(path -> {
                    List<ConsumerProviderPair> consumerProviderPairs = new ArrayList<>();
                    //get index of target service
                    int targetIndex = getTargetIndex(target, path);
                    //generate provider and consumer pair
                    for (int i = 1; i < path.size(); i++) {
                        String consumerServiceName = IDExtractor.getServiceName(path.get(i - 1));
                        String providerServiceName = IDExtractor.getServiceName(path.get(i));

                        ConsumerProviderPair consumerProviderPair = new ConsumerProviderPair(providerServiceName, consumerServiceName, targetIndex - i);
                        consumerProviderPairs.add(consumerProviderPair);
                    }
                    return consumerProviderPairs.stream();
                })
                .sorted(Comparator.comparingInt(ConsumerProviderPair::getOrder))
                .collect(Collectors.toList());
    }

    private int getTargetIndex(String target, List<String> path) {
        int targetIndex = -1;
        for (int i = path.size() - 1; i >= 0; i--) {
            if(IDExtractor.getServiceName(path.get(i)).equals(target)){
                targetIndex = i;
                break;
            }
        }
        return targetIndex;
    }

    public List<String> getScenarioAnnotations(List<List<String>> paths, String target) {
        List<List<String>> targetEndpoints = getTargetEndpoints(paths, target);
        return targetEndpoints.stream()
                .flatMap(path -> path.stream().map(endpoint -> endpoint.split(" ")[0])).distinct()
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
