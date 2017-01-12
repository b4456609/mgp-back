package soselab.mpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;

@Service
public class GraphService {

    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;


    public GraphVisualization getVisualizationData() {
        return null;
    }
}
