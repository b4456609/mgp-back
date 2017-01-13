package soselab.mpg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphService.class);

    @Autowired
    GraphVisualizationFactory graphVisualizationFactory;
    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;
    @Autowired
    private ServiceNameRepository serviceNameRepository;

    public GraphVisualization getVisualizationData() {
        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        LOGGER.info("all service names {}",serviceNames.toString());

        List<MicroserviceProjectDescription> microserviceProjectDescriptions = serviceNames.stream().map(serviceName -> {
            return microserviceProjectDescriptionRepository
                    .findFirstByNameOrderByTimestampAsc(serviceName.getServiceName());
        }).collect(Collectors.toList());

        return graphVisualizationFactory.create(microserviceProjectDescriptions);
    }
}
