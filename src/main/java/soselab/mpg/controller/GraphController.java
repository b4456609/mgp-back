package soselab.mpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.service.GraphService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class GraphController {

    @Autowired
    private GraphService graphService;

    @RequestMapping(path = "/graph", method = RequestMethod.GET)
    public GraphVisualization getGraphData() {
        return graphService.getVisualizationData();
    }
}
