package soselab.mpg.bdd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.controller.dto.GitUrlDTO;
import soselab.mpg.bdd.service.BDDService;

@RestController
@RequestMapping("/api/bdd")
public class BDDController {

    private final BDDService bddService;

    @Autowired
    public BDDController(BDDService bddService) {
        this.bddService = bddService;
    }

    @PostMapping
    public void updateBDDGitSetting(@RequestBody GitUrlDTO gitUrlDTO) {
        String url = gitUrlDTO.getUrl();
        bddService.updateGitUrl(url);
    }
}
