package soselab.mpg.regression;

import org.springframework.stereotype.Service;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegressionPicker {
    public List<ConsumerProviderPair> getRegressionServiceTestPair(List<List<String>> paths, String target) {
        List<List<String>> targetEndpoints = getTargetEndpoints(paths, target);
        return targetEndpoints.parallelStream()
                .flatMap(path -> {
                    List<ConsumerProviderPair> consumerProviderPairs = new ArrayList<>();
                    for (int i = 1; i < path.size(); i++) {
                        String consumerEndpoint = path.get(i - 1);
                        String[] consumerendpointSplit = consumerEndpoint.split(" ");
                        String consumerServiceName = consumerendpointSplit[0];

                        String providerEndpoint = path.get(i);
                        String[] provicerEndpointSplit = providerEndpoint.split(" ");
                        String providerServiceName = provicerEndpointSplit[0];

                        ConsumerProviderPair consumerProviderPair = new ConsumerProviderPair(providerServiceName, consumerServiceName);
                        consumerProviderPairs.add(consumerProviderPair);
                    }
                    return consumerProviderPairs.stream();
                })
                .collect(Collectors.toList());
    }

    public List<String> getScenarioAnnotations(List<List<String>> paths, String target) {
        List<List<String>> targetEndpoints = getTargetEndpoints(paths, target);
        return targetEndpoints.parallelStream()
                .flatMap(path -> path.stream().map(endpoint -> endpoint.split(" ")[0])).distinct()
                .collect(Collectors.toList());
    }

    private List<List<String>> getTargetEndpoints(List<List<String>> paths, String target) {
        return paths
                .parallelStream()
                .map(path -> {
                    int lastIndex = -1;
                    for (int i = 0; i < path.size(); i++) {
                        String endpint = path.get(i);
                        if (endpint.contains(target)) {
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
