package soselab.mpg.codegen.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import soselab.mpg.codegen.model.CodeSnippet;

@Component
public class CodeGenClientImpl implements CodeGenClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeGenClientImpl.class);
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${mpg.codegen.url}")
    private String url;

    @Override
    public CodeSnippet getCodeSnippet(String httpMethod, String path) {
        LOGGER.info("codegen url {}", url);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("method", httpMethod)
                .queryParam("url", "http://host" + path)
                .toUriString();
        LOGGER.info("request uri {}", uri);
        CodeSnippet codeSnippet = restTemplate.getForObject(uri, CodeSnippet.class);
        return codeSnippet;
    }
}