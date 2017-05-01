package soselab.mpg.regression.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soselab.mpg.graph.model.PathGroup;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.mpd.model.IDExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractRegressionPicker<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRegressionPicker.class);
    private final GraphService graphService;
    private String target;
    private int regressionNum;
    private List<List<String>> paths;
    private List<List<String>> targetPath;
    private List<T> result;
    private List<List<T>> runResult;

    protected AbstractRegressionPicker(GraphService graphService) {
        this.graphService = graphService;
    }

    public List<List<T>> pickTestCase(String target, int regressionNum) {
        this.target = target;
        this.regressionNum = regressionNum;

        getAllPath();
        getTargetpath();
        filterOutUnnessaryEndpoint();
        result = getTestCaseResult(targetPath, target);
        generateRunLists();

        return runResult;
    }

    protected abstract List<T> getTestCaseResult(List<List<String>> targetPath, String target);

    private void getAllPath() {
        List<PathGroup> pathNodeIdGroups = graphService.getPathNodeIdGroups();
        paths = pathNodeIdGroups.stream()
                .flatMap(pathGroup -> pathGroup.getPaths().stream())
                .collect(Collectors.toList());
    }

    private void getTargetpath() {
        this.targetPath = paths
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
                .filter(path -> !path.isEmpty())
                .collect(Collectors.toList());
    }

    private void filterOutUnnessaryEndpoint() {
        targetPath = targetPath
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

    private void generateRunLists() {
        LOGGER.debug("all regression test: {}", result);
        this.runResult = new ArrayList<>();

        for (int i = 0; i < this.result.size(); ) {
            //get num item insert to list
            if (i + regressionNum < this.result.size()) {
                List<T> temp = this.result.subList(i, i + regressionNum);
                this.runResult.add(temp);
                i += regressionNum;
            } else {
                List<T> temp = this.result.subList(i, this.result.size());
                this.runResult.add(temp);
                break;
            }
        }
    }

    protected int getTargetIndex(String target, List<String> path) {
        int targetIndex = -1;
        for (int i = path.size() - 1; i >= 0; i--) {
            if (IDExtractor.getServiceName(path.get(i)).equals(target)) {
                targetIndex = i;
                break;
            }
        }
        return targetIndex;
    }

}
