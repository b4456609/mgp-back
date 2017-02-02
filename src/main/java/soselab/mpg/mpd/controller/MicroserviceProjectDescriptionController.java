package soselab.mpg.mpd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soselab.mpg.graph.service.MicroserviceGraphBuilderService;
import soselab.mpg.mpd.service.MPDService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class MicroserviceProjectDescriptionController {

    private final MPDService MPDService;
    private final MicroserviceGraphBuilderService microserviceGraphBuilderService;

    @Autowired
    public MicroserviceProjectDescriptionController(MPDService MPDService, MicroserviceGraphBuilderService microserviceGraphBuilderService) {
        this.MPDService = MPDService;
        this.microserviceGraphBuilderService = microserviceGraphBuilderService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public void uploadMdpFile(@RequestParam("file") MultipartFile file) {
        try {
            String json = new String(file.getBytes());
            MPDService.uploadFile(json);
            microserviceGraphBuilderService.build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileCannotReadException();
        }
    }

}
