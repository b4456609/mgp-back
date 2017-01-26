package soselab.mpg.graph.service;

import soselab.mpg.graph.controller.dto.EndpointInformationDTO;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.controller.dto.ServiceCallInformationDTO;
import soselab.mpg.graph.controller.dto.ServiceInformationDTO;
import soselab.mpg.graph.model.PathGroup;

import java.util.List;

/**
 * Created by bernie on 2017/1/22.
 */
public interface GraphService {
    GraphDataDTO getVisualizationData();

    List<PathGroup> getPathNodeIdGroups();

    List<List<String>> getCyclicGroups(List<List<String>> pathNodeIdGroups);

    List<ServiceInformationDTO> getServiceInfo();

    List<EndpointInformationDTO> getEndpointInformations();

    List<ServiceCallInformationDTO> getProviderConsumerPair();
}
