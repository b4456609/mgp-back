package soselab.mpg.pact.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.pact.client.PactClient;
import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.repository.PactConfigRepository;
import soselab.mpg.pact.repository.PactRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PactServiceImp implements PactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PactServiceImp.class);

    @Autowired
    private PactConfigRepository pactConfigRepository;

    @Autowired
    private PactRepository pactRepository;

    @Autowired
    private PactClient pactClient;

    @Override
    public void setPactService(PactConfig pactConfig) {
        pactConfigRepository.deleteAll();
        pactConfigRepository.save(pactConfig);
    }

    @Override
    public PactConfig getPactConfig() {
        return pactConfigRepository.findAll().get(0);
    }

    @Override
    public void getLatestPactFile() {
        String pactUrl = pactConfigRepository.findAll().get(0).getUrl() + "pacts/latest";
        LOGGER.info("pact url: {}", pactUrl);
        List<String> pactFileLinks = pactClient.getPactFileLinks(pactUrl);
        List<Pact> pacts = pactFileLinks.stream().map(link -> {
            String pactJson = pactClient.getPactJson(link);
            String[] split = link.split("/");
            String provider = split[5];
            String consumber = split[7];
            String version = split[9];
            return new Pact(provider, consumber, pactJson, version);
        }).collect(Collectors.toList());
        LOGGER.info("pact objects {}", pacts.toString());
        pactRepository.deleteAll();
        pactRepository.save(pacts);
    }
}
