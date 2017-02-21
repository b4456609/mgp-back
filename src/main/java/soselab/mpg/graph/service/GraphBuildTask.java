package soselab.mpg.graph.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.service.handler.GraphBuildHandler;
import soselab.mpg.graph.service.handler.ScenarioBuildHandler;
import soselab.mpg.graph.service.handler.ServiceBuildHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class GraphBuildTask {
    private final ServiceBuildHandler serviceBuildHandler;
    private final ScenarioBuildHandler scenarioBuildHandler;

    public GraphBuildTask(ServiceBuildHandler serviceBuildHandler, ScenarioBuildHandler scenarioBuildHandler) {
        this.serviceBuildHandler = serviceBuildHandler;
        this.scenarioBuildHandler = scenarioBuildHandler;
    }

    @Async
    public Future<Boolean> buildGraph(AtomicBoolean needBuild) {
        List<GraphBuildHandler> graphBuildHandlers = new ArrayList<GraphBuildHandler>();
        // the execute order, the scenario is depend on service build result
        graphBuildHandlers.add(serviceBuildHandler);
        graphBuildHandlers.add(scenarioBuildHandler);

        while (needBuild.getAndSet(false)) {
            for (GraphBuildHandler graphBuildHandler : graphBuildHandlers) {
                if (needBuild.get()) {
                    break;
                }
                graphBuildHandler.build();
            }
        }
        return new AsyncResult<Boolean>(true);
    }
}