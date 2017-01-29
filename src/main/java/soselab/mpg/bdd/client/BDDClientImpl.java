package soselab.mpg.bdd.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soselab.mpg.bdd.client.dto.FeatureDTO;
import soselab.mpg.bdd.client.dto.LatestCommitStatusDTO;
import soselab.mpg.bdd.client.dto.UrlDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class BDDClientImpl implements BDDClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BDDClientImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${mpg.bdd.url}")
    private String url;


    @Override
    public LatestCommitStatusDTO gitClone(String url) {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(url);
        LatestCommitStatusDTO latestCommitStatusDTO = restTemplate
                .postForObject(this.url + "/gitClone", urlDTO, LatestCommitStatusDTO.class);
        return latestCommitStatusDTO;
    }

    @Override
    public LatestCommitStatusDTO pull() {
        LatestCommitStatusDTO latestCommitStatusDTO = restTemplate
                .postForObject(this.url + "/pull", null, LatestCommitStatusDTO.class);
        return latestCommitStatusDTO;
    }

    @Override
    public LatestCommitStatusDTO getLastestCommitStatus() {
        LatestCommitStatusDTO latestCommitStatusDTO = restTemplate
                .getForObject(this.url + "/latestCommit", LatestCommitStatusDTO.class);
        return latestCommitStatusDTO;
    }


    @Override
    public List<FeatureDTO> getParseData() {
        FeatureDTO[] forObject = restTemplate
                .getForObject(this.url + "/parse", FeatureDTO[].class);
        return Arrays.asList(forObject);
    }


}
