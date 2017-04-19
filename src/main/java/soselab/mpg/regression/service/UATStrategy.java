package soselab.mpg.regression.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.mpd.service.EndpointAnnotationBuilder;
import soselab.mpg.regression.model.AnnotationWithOrder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UATStrategy extends AbstractRegressionPicker<String> {

    @Autowired
    public UATStrategy(GraphService graphService) {
        super(graphService);
    }

    @Override
    protected List<String> getTestCaseResult(List<List<String>> targetPath, String target) {
        Map<String, AnnotationWithOrder> endpointAndAnnotation = new HashMap<>();
        targetPath.forEach(path -> {
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
                .map(AnnotationWithOrder::getId)
                .collect(Collectors.toList());
    }
}
