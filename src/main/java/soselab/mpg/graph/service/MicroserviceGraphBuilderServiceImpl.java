package soselab.mpg.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soselab.mpg.graph.service.handler.GraphBuildHandler;

import java.util.List;

@Service
public class MicroserviceGraphBuilderServiceImpl implements MicroserviceGraphBuilderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceGraphBuilderServiceImpl.class);

    private final List<GraphBuildHandler> graphBuildHandlers;

    public MicroserviceGraphBuilderServiceImpl(List<GraphBuildHandler> graphBuildHandlers) {
        this.graphBuildHandlers = graphBuildHandlers;
    }

    @Override
    public synchronized void build() {
        for (GraphBuildHandler graphBuildHandler : graphBuildHandlers) {
            graphBuildHandler.build();
        }
    }
}