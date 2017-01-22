package soselab.mpg.pact.client;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PactClientImpl implements PactClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(PactClientImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<String> getPactFileLinks(String pactUrl) {
        String response = restTemplate.getForObject(pactUrl, String.class);
        // get all links
        List<String> links = JsonPath.read(response, "$..href");
        LOGGER.info("jsonpath links {}", links);

        List<String> pactLinks = links.stream()
                //get latest version pact links
                .filter(link -> link.contains("version") && link.contains("consumer"))
                //unescape string
                .map(link -> {
                    StringEscapeUtils.unescapeJava(link);
                    return link;
                })
                .collect(Collectors.toList());
        LOGGER.info("pact link {}", pactLinks);


        return pactLinks;
    }

    @Override
    public String getPactJson(String pactLink) {
        String response = restTemplate.getForObject(pactLink, String.class);
        LOGGER.debug(response);
        return response;
    }
}
