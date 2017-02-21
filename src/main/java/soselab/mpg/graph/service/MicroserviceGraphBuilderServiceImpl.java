package soselab.mpg.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MicroserviceGraphBuilderServiceImpl implements MicroserviceGraphBuilderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceGraphBuilderServiceImpl.class);
    private final AtomicBoolean needBuild = new AtomicBoolean(false);
    private final GraphBuildTask graphBuildTask;
    private Future<Boolean> building;

    @Autowired
    public MicroserviceGraphBuilderServiceImpl(GraphBuildTask graphBuildTask) {
        this.graphBuildTask = graphBuildTask;
    }

    @Override
    public synchronized Future<Boolean> build() {
        if (!building.isDone()) {
            needBuild.set(true);
        } else {
            building = graphBuildTask.buildGraph(needBuild);
        }
        return building;
    }
}