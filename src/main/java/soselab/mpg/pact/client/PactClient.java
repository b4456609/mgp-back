package soselab.mpg.pact.client;

import java.util.List;

public interface PactClient {
    List<String> getPactFileLinks(String pactUrl);

    String getPactJson(String pactLink);
}
