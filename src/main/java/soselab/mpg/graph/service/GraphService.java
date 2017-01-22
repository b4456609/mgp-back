package soselab.mpg.graph.service;

import soselab.mpg.graph.controller.dto.GraphVisualization;
import soselab.mpg.graph.controller.dto.ServiceInfoDTO;

import java.util.List;

/**
 * Created by bernie on 2017/1/22.
 */
public interface GraphService {
    GraphVisualization getVisualizationData();

    void buildGraphFromLatestMicroserviceProjectDescription();

    List<List<String>> getPathNodeIdGroups();

    List<ServiceInfoDTO> getServiceInfo();
}
