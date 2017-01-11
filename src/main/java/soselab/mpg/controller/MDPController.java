package soselab.mpg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soselab.mpg.mpd.model.Mdp;
import soselab.mpg.service.MdpService;

import java.io.IOException;

/**
 * Created by bernie on 1/11/17.
 */
@RestController
@RequestMapping(path = "/api")
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
