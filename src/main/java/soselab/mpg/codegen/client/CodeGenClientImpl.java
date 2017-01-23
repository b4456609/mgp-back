package soselab.mpg.codegen.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import soselab.mpg.codegen.model.CodeSnippet;

@Component
public class CodeGenClientImpl implements CodeGenClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeGenClientImpl.class);

    @Value("${mpg.codegen.url}")
    private String url;

    private final RestTemplate restTemplate;

    @Autowired
    public CodeGenClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CodeSnippet getCodeSnippet(String httpMethod, String path) {
        LOGGER.debug("codegen url {}", url);
        String uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("httpMethod", httpMethod)
                .queryParam("path", path)
                .toUriString();
        LOGGER.debug("request uri {}", uri);
        CodeSnippet codeSnippet = restTemplate.getForObject(uri, CodeSnippet.class);
        LOGGER.debug(codeSnippet.toString());
        return codeSnippet;
    }
}
