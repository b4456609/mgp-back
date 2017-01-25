package soselab.mpg.pact.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.pact.client.PactClient;
import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.repository.PactConfigRepository;
import soselab.mpg.pact.repository.PactRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PactServiceImp implements PactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PactServiceImp.class);

    private final PactConfigRepository pactConfigRepository;
    private final PactRepository pactRepository;
    private final PactClient pactClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public PactServiceImp(PactConfigRepository pactConfigRepository, PactRepository pactRepository, PactClient pactClient, ObjectMapper objectMapper) {
        this.pactConfigRepository = pactConfigRepository;
        this.pactRepository = pactRepository;
        this.pactClient = pactClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setPactService(PactConfig pactConfig) {
        pactConfigRepository.deleteAll();
        pactConfigRepository.save(pactConfig);
    }

    @Override
    public PactConfig getPactConfig() {
        List<PactConfig> all = pactConfigRepository.findAll();
        if (all.isEmpty()) throw new NoPactConfigException();
        return all.get(0);
    }

    @Override
    public void getLatestPactFile() {
        List<PactConfig> all = pactConfigRepository.findAll();
        if (all.isEmpty())
            return;
        String pactUrl = all.get(0).getUrl() + "pacts/latest";
        LOGGER.info("pact url: {}", pactUrl);
        List<String> pactFileLinks = pactClient.getPactFileLinks(pactUrl);
        List<Pact> pacts = pactFileLinks.stream().map(link -> {
            String pactJson = pactClient.getPactJson(link);
            String indented = null;
            try {
                Object json = objectMapper.readValue(pactJson, Object.class);
                indented = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] split = link.split("/");
            String provider = split[5];
            String consumber = split[7];
            String version = split[9];
            return new Pact(provider, consumber, indented, version);
        }).collect(Collectors.toList());
        LOGGER.info("pact objects {}", pacts.toString());
        pactRepository.deleteAll();
        pactRepository.save(pacts);
    }

    @Override
    public List<Pact> getPacts() {
        return pactRepository.findAll();
    }
}
