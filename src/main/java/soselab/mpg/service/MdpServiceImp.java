package soselab.mpg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.mpd.MdpReader;
import soselab.mpg.mpd.model.Mdp;
import soselab.mpg.repository.MdpRepository;
import soselab.mpg.service.exception.MdpDeserializeException;

/**
 * Created by bernie on 1/11/17.
 */
@Service
public class MdpServiceImp implements MdpService {

    @Autowired
    private MdpRepository mdpRepository;

    @Autowired
    private MdpReader mdpReader;

    @Override
    public void uploadFile(String json) {
        Mdp mdp = mdpReader
                .readMDP(json)
                .orElseThrow(MdpDeserializeException::new);

        mdpRepository.save(mdp);
    }
}
