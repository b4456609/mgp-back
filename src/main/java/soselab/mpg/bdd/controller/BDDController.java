package soselab.mpg.bdd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.bdd.service.BDDService;

import java.util.List;

@RestController
@RequestMapping("/api/bdd")
public class BDDController {
    private final BDDService bddService;

    @Autowired
    public BDDController(BDDService bddService) {
        this.bddService = bddService;
    }

    @GetMapping
    public List<FeatureDocumentDTO> getAllBDDFeature() {
        return bddService.getFeatures();
    }
}
