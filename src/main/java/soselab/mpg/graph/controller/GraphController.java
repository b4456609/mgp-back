package soselab.mpg.graph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.graph.controller.dto.GraphVisualization;
import soselab.mpg.graph.service.GraphService;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin
public class GraphController {

    @Autowired
    private GraphService graphService;

    @GetMapping
    public GraphVisualization getGraphData() {
        return graphService.getVisualizationData();
    }
}
