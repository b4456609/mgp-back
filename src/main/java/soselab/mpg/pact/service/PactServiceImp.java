package soselab.mpg.pact.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soselab.mpg.pact.client.PactClient;
import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.model.ServiceCallRelationInformation;
import soselab.mpg.pact.repository.PactConfigRepository;
import soselab.mpg.pact.repository.PactRepository;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.io.IOException;
import java.util.Collections;
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
    public void updatePactUrl(String url) {
        LOGGER.info("update pact url {}", url);
        if (url == null)
            return;
        pactConfigRepository.deleteAll();
        if (url.equals("")) return;
        PactConfig pactConfig = new PactConfig();
        pactConfig.setUrl(url);
        pactConfigRepository.save(pactConfig);
    }

    @Override
    public String getPactGitUrl() {
        List<PactConfig> all = pactConfigRepository.findAll();
        if (all.isEmpty()) return "";
        return all.get(0).getUrl();
    }

    @Override
    public void getLatestPactFile() {
        List<PactConfig> all = pactConfigRepository.findAll();
        if (all.isEmpty())
            return;
        String pactUrl = all.get(0).getUrl() + "pacts/latest";
        LOGGER.info("pact url: {}", pactUrl);
        List<String> pactFileLinks = pactClient.getPactFileLinks(pactUrl);
        List<ServiceCallRelationInformation> serviceCallRelationInformations = pactFileLinks.stream().map(link -> {
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
            String consumer = split[7];
            String version = split[9];
            return new ServiceCallRelationInformation(provider, consumer, indented, version);
        }).collect(Collectors.toList());
        LOGGER.info("pact objects {}", serviceCallRelationInformations.toString());
        pactRepository.deleteAll();
        pactRepository.save(serviceCallRelationInformations);
    }

    @Override
    public List<ServiceCallRelationInformation> getPacts() {
        return pactRepository.findAll();
    }

    @Override
    public List<String> getPactUrlByConsumerAndProvider(List<ConsumerProviderPair> serviceTestPair) {
        List<PactConfig> all = pactConfigRepository.findAll();
        if (all.isEmpty())
            return Collections.emptyList();
        String pactUrl = all.get(0).getUrl() + "pacts/latest";
        LOGGER.info("pact url: {}", pactUrl);
        List<String> pactFileLinks = pactClient.getPactFileLinks(pactUrl);
        return pactFileLinks.stream()
                .filter(link -> {
                    String[] split = link.split("/");
                    String provider = split[5];
                    String consumer = split[7];
                    return serviceTestPair.stream()
                            .anyMatch(pair -> {
                                return pair.getProvider().equals(provider) && pair.getConsumer().equals(consumer);
                            });
                }).collect(Collectors.toList());
    }
}
