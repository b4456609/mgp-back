package soselab.mpg.graph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soselab.mpg.graph.service.handler.GraphBuildHandler;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;

import java.util.List;

@Service
public class MicroserviceGraphBuilderServiceImpl implements MicroserviceGraphBuilderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceGraphBuilderServiceImpl.class);

    private final List<GraphBuildHandler> graphBuildHandlers;

    private List<MicroserviceProjectDescription> microserviceProjectDescriptions;

    public MicroserviceGraphBuilderServiceImpl(List<GraphBuildHandler> graphBuildHandlers) {
        this.graphBuildHandlers = graphBuildHandlers;
    }

    @Override
    public synchronized void build(List<MicroserviceProjectDescription> microserviceProjectDescriptions) {
        this.microserviceProjectDescriptions = microserviceProjectDescriptions;
        for (GraphBuildHandler graphBuildHandler : graphBuildHandlers) {
            graphBuildHandler.build(this);
        }
    }

    public List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions() {
        return microserviceProjectDescriptions;
    }
}