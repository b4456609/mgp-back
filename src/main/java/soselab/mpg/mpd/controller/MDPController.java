package soselab.mpg.mpd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soselab.mpg.mpd.service.MdpService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class MDPController {

    @Autowired
    private MdpService mdpService;

    @RequestMapping(path ="/mpd", method = RequestMethod.POST)
    public void uploadMdpFile(@RequestParam("file") MultipartFile file){
        try {
            String json = new String(file.getBytes());
            mdpService.uploadFile(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileCannotReadException();
        }
    }

}
