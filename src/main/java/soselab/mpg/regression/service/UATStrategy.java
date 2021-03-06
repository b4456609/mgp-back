package soselab.mpg.regression.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.repository.ScenarioNodeRepository;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.mpd.service.MPDService;
import soselab.mpg.regression.model.EndpointWithOrder;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UATStrategy extends AbstractRegressionPicker<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UATStrategy.class);

    private final ScenarioNodeRepository scenarioNodeRepository;
    private final MPDService mpdService;

    @Autowired
    public UATStrategy(GraphService graphService, ScenarioNodeRepository scenarioNodeRepository, MPDService mpdService) {
        super(graphService);
        this.scenarioNodeRepository = scenarioNodeRepository;
        this.mpdService = mpdService;
    }

    @Override
    protected List<String> getTestCaseResult(List<List<String>> targetPath, String target) {
        List<String> serviceEndpoints = mpdService.getServiceEndpoints(target);
        List<String> scenarioByTargetEndpoints = scenarioNodeRepository.getScenarioByEndpoints(serviceEndpoints);
        LOGGER.debug("{}", targetPath);
        if (targetPath.isEmpty()) return scenarioByTargetEndpoints;


        Map<String, EndpointWithOrder> endpointAndAnnotation = new HashMap<>();
        targetPath.forEach(path -> {
            int targetIndex = getTargetIndex(target, path);
            for (int i = 0; i < path.size(); i++) {
                EndpointWithOrder endpointWithOrder = endpointAndAnnotation.get(path.get(i));
                if (endpointWithOrder == null || endpointWithOrder.getOrder() > targetIndex - i) {
                    endpointAndAnnotation.put(path.get(i),
                            new EndpointWithOrder(targetIndex - i, path.get(i)));
                }
            }
        });
        LOGGER.debug("{}", endpointAndAnnotation);

        List<EndpointWithOrder> sortedEndpointList = endpointAndAnnotation.values().stream()
                .sorted(Comparator.comparingInt(EndpointWithOrder::getOrder))
                .collect(Collectors.toList());

        //add this service endpoint
        List<String> result = new ArrayList<>(scenarioByTargetEndpoints);
        HashSet<String> sets = new HashSet<>(scenarioByTargetEndpoints);

        int maxOrder = sortedEndpointList.get(sortedEndpointList.size() - 1).getOrder();
        for (int i = -1; i <= maxOrder; i++) {
            final int index = i;
            List<String> endpoints = sortedEndpointList.stream()
                    .filter(item -> item.getOrder() == index)
                    .map(EndpointWithOrder::getId)
                    .collect(Collectors.toList());
            List<String> scenarioByEndpoints = scenarioNodeRepository.getScenarioByEndpoints(endpoints);
            Set<String> scenarios = new HashSet<>(scenarioByEndpoints);
            scenarios.removeAll(sets);
            sets.addAll(scenarios);
            result.addAll(scenarios);
        }
        return result;
    }
}
