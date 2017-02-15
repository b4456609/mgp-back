package soselab.mpg.graph.service;

import soselab.mpg.graph.controller.dto.EndpointInformationDTO;
import soselab.mpg.graph.controller.dto.GraphDataDTO;
import soselab.mpg.graph.controller.dto.ServiceCallInformationDTO;
import soselab.mpg.graph.controller.dto.ServiceInformationDTO;
import soselab.mpg.graph.model.PathGroup;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bernie on 2017/1/22.
 */
public interface GraphService {
    GraphDataDTO getVisualizationData(Map<String, Set<String>> errorMarkConsumerAndProvider);

    List<PathGroup> getPathNodeIdGroups();

    void setCyclicGroups(List<PathGroup> pathNodeIdGroups);

    List<ServiceInformationDTO> getServiceInfo();

    List<EndpointInformationDTO> getEndpointInformations();

    List<ServiceCallInformationDTO> getProviderConsumerPair();

}
