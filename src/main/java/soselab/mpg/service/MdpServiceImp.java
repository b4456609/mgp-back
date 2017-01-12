package soselab.mpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.model.ServiceName;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;
import soselab.mpg.mpd.MicroserviceProjectDescriptionReader;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.mongo.ServiceNameRepository;
import soselab.mpg.service.exception.MicroserviceProjectDescriptionDeserializeException;

@Service
public class MdpServiceImp implements MdpService {

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
