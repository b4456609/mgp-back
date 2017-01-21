package soselab.mpg.pact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.repository.PactRepository;

@Service
public class PactServiceImp implements PactService {

    @Autowired
    private PactRepository pactRepository;

    @Override
    public void setPactService(PactConfig pactConfig) {
        pactRepository.deleteAll();
        pactRepository.save(pactConfig);
    }

    @Override
    public PactConfig getPactConfig() {
        return pactRepository.findAll().get(0);
    }
}
