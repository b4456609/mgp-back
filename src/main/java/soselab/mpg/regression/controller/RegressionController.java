package soselab.mpg.regression.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soselab.mpg.regression.controller.dto.PactTestCaseDTO;
import soselab.mpg.regression.service.ServiceTestStrategy;
import soselab.mpg.regression.service.UATStrategy;

import java.util.List;

@RestController
@RequestMapping("/api/regression")
public class RegressionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegressionController.class);
    private final ServiceTestStrategy serviceTestStrategy;
    private final UATStrategy uatStrategy;


    @Autowired
    public RegressionController(ServiceTestStrategy serviceTestStrategy, UATStrategy uatStrategy) {
        this.serviceTestStrategy = serviceTestStrategy;
        this.uatStrategy = uatStrategy;
    }

    @GetMapping("/serviceTest/{serviceName}")
    public List<List<PactTestCaseDTO>> getRegressionServiceTest(@PathVariable("serviceName") String serviceName,
                                                                @RequestParam(required = false, defaultValue = "5", value = "num") int num) {
        return serviceTestStrategy.pickTestCase(serviceName, num);
    }

    @GetMapping("/uat/{serviceName}")
    public List<List<String>> getScenarioAnnotations(@PathVariable("serviceName") String serviceName,
                                                     @RequestParam(required = false, defaultValue = "5", value = "num") int num) {
        return uatStrategy.pickTestCase(serviceName, num);
    }
}
