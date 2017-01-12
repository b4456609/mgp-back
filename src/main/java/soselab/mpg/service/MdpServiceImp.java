package soselab.mpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.mpd.MicroserviceProjectDescriptionReader;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.repository.mongo.MdpRepository;
import soselab.mpg.service.exception.MicroserviceProjectDescriptionDeserializeException;

@Service
public class MdpServiceImp implements MdpService {

    @Autowired
    private MdpRepository mdpRepository;

    @Autowired
    private MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader;

    @Override
    public void uploadFile(String json) {
        MicroserviceProjectDescription mdp = microserviceProjectDescriptionReader
                .readMDP(json)
                .orElseThrow(MicroserviceProjectDescriptionDeserializeException::new);

        mdpRepository.save(mdp);
    }
}
