package soselab.mpg.mpd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soselab.mpg.mpd.service.MPDService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class MicroserviceProjectDescriptionController {

    private final MPDService MPDService;

    @Autowired
    public MicroserviceProjectDescriptionController(soselab.mpg.mpd.service.MPDService MPDService) {
        this.MPDService = MPDService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public void uploadMdpFile(@RequestParam("file") MultipartFile file) {
        try {
            String json = new String(file.getBytes());
            MPDService.uploadFile(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileCannotReadException();
        }
    }

}
