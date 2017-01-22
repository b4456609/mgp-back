package soselab.mpg.graph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.graph.controller.dto.ServiceInfoDTO;
import soselab.mpg.graph.service.GraphService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/serviceInfo")
public class ServiceInfoController {

    private final GraphService graphService;

    @Autowired
    public ServiceInfoController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping
    public List<ServiceInfoDTO> getServiceInfo() {
        return graphService.getServiceInfo();
    }
}
