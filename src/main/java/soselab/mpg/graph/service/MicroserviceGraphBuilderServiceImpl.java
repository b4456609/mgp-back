package soselab.mpg.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.graph.service.handler.GraphBuildHandler;
import soselab.mpg.graph.service.handler.ScenarioBuildHandler;
import soselab.mpg.graph.service.handler.ServiceBuildHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MicroserviceGraphBuilderServiceImpl implements MicroserviceGraphBuilderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceGraphBuilderServiceImpl.class);
    private final AtomicBoolean needBuild = new AtomicBoolean(false);
    private final ServiceBuildHandler serviceBuildHandler;
    private final ScenarioBuildHandler scenarioBuildHandler;
    private Thread thread = null;

    @Autowired
    public MicroserviceGraphBuilderServiceImpl(ServiceBuildHandler serviceBuildHandler,
                                               ScenarioBuildHandler scenarioBuildHandler) {
        this.serviceBuildHandler = serviceBuildHandler;
        this.scenarioBuildHandler = scenarioBuildHandler;
    }

    @Override
    public synchronized void build() {
        if (thread != null && thread.isAlive()) {
            needBuild.set(true);
        } else {
            this.start();
        }
    }

    private void start() {
        this.thread = new Thread(this::buildGraph);
        thread.start();
    }

    private void buildGraph() {
        // the execute order, the scenario is depend on service build result
        List<GraphBuildHandler> graphBuildHandlers = new ArrayList<>();
        graphBuildHandlers.add(serviceBuildHandler);
        graphBuildHandlers.add(scenarioBuildHandler);

        for (GraphBuildHandler graphBuildHandler : graphBuildHandlers) {
            if (needBuild.get()) {
                break;
            }
            graphBuildHandler.build();
        }
        if (needBuild.getAndSet(false)) {
            this.start();
        }
    }
}