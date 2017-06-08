package soselab.mpg.regression.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Get Service Test Regression Test Cases")
    public List<List<PactTestCaseDTO>> getRegressionServiceTest(@ApiParam(value = "Service Name", required = true)
                                                                @PathVariable("serviceName") String serviceName,
                                                                @ApiParam(value = "Number of Test Cases per Iteration", defaultValue = "5")
                                                                @RequestParam(required = false, defaultValue = "5", value = "num") int num) {
        return serviceTestStrategy.pickTestCase(serviceName, num);
    }

    @GetMapping("/uat/{serviceName}")
    @ApiOperation(value = "Get UAT Regression Regression Test Cases")
    public List<List<String>> getScenarioAnnotations(@ApiParam(value = "Service Name", required = true)
                                                     @PathVariable("serviceName") String serviceName,
                                                     @ApiParam(value = "Number of Test Cases per Iteration", defaultValue = "5")
                                                     @RequestParam(required = false, defaultValue = "5", value = "num") int num) {
        return uatStrategy.pickTestCase(serviceName, num);
    }
}
