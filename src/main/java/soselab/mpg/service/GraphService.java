package soselab.mpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.factory.GraphVisualizationFactory;
import soselab.mpg.model.ServiceName;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraphService {

    @Autowired
    GraphVisualizationFactory graphVisualizationFactory;
    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;
    @Autowired
    private ServiceNameRepository serviceNameRepository;

    public GraphVisualization getVisualizationData() {
        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        List<MicroserviceProjectDescription> microserviceProjectDescriptions = serviceNames.stream().map(serviceName -> {
            return microserviceProjectDescriptionRepository
                    .findFirstByNameOrderByTimestampAsc(serviceName.getServiceName());
        }).collect(Collectors.toList());

        return graphVisualizationFactory.create(microserviceProjectDescriptions);
    }
}
