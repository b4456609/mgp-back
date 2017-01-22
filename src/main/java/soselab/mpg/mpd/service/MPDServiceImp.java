package soselab.mpg.mpd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.mpd.MicroserviceProjectDescriptionReader;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.model.ServiceName;
import soselab.mpg.mpd.repository.MicroserviceProjectDescriptionRepository;
import soselab.mpg.mpd.repository.ServiceNameRepository;
import soselab.mpg.mpd.service.exception.MicroserviceProjectDescriptionDeserializeException;

@Service
public class MPDServiceImp implements MPDService {

    @Autowired
    private MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;

    @Autowired
    private MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader;

    @Autowired
    private ServiceNameRepository serviceNameRepository;

    @Override
    public void uploadFile(String json) {
        MicroserviceProjectDescription mdp = microserviceProjectDescriptionReader
                .readMDP(json)
                .orElseThrow(MicroserviceProjectDescriptionDeserializeException::new);

        microserviceProjectDescriptionRepository.save(mdp);
        serviceNameRepository.save(new ServiceName(mdp.getName(), mdp.getName()));
    }
}
