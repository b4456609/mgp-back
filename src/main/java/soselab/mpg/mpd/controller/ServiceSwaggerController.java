package soselab.mpg.mpd.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soselab.mpg.mpd.service.MPDService;

@RestController
@RequestMapping("/api/swagger")
public class ServiceSwaggerController {
    private final MPDService mpdService;

    @Autowired
    public ServiceSwaggerController(MPDService mpdService) {
        this.mpdService = mpdService;
    }

    @GetMapping(path = "/{serviceName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get microservice's swagger")
    public String swagger(@ApiParam(value = "Service Name", required = true)
                          @PathVariable("serviceName") String serviceName) {
        return mpdService.getServiceSwagger(serviceName);
    }
}
