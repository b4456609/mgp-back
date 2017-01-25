package soselab.mpg.mpd.service;

import soselab.mpg.mpd.model.MicroserviceProjectDescription;

import java.util.List;

/**
 * Created by bernie on 1/11/17.
 */
public interface MPDService {
    void uploadFile(String json);

    List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions();

    String getServiceSwagger(String serviceName);
}
