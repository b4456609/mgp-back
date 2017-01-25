package soselab.mpg.graph.service;

import soselab.mpg.graph.controller.dto.EndpointInformationDTO;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.controller.dto.ServiceInformationDTO;

import java.util.List;

/**
 * Created by bernie on 2017/1/22.
 */
public interface GraphService {
    GraphDataDTO getVisualizationData();

    List<List<String>> getPathNodeIdGroups();

    List<ServiceInformationDTO> getServiceInfo();

    List<EndpointInformationDTO> getEndpointInformations();
}
