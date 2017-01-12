package soselab.mpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.dto.graph.GraphVisualization;
import soselab.mpg.service.GraphService;

/**
 * Created by bernie on 2017/1/12.
 */
@RestController
@RequestMapping("/api")
public class GraphController {

    @Autowired
    GraphService graphService;

    @RequestMapping(path = "/graph", method = RequestMethod.GET)
    public GraphVisualization getGraphData() {
        return graphService.getVisualizationData();
    }
}
