package soselab.mpg.graph.service;

import soselab.mpg.mpd.model.MicroserviceProjectDescription;

import java.util.List;

/**
 * Created by Fan on 2017/1/25.
 */
public interface MicroserviceGraphBuilderService {
    void build(List<MicroserviceProjectDescription> microserviceProjectDescriptions);
}
