package soselab.mpg.pact.service;

import soselab.mpg.pact.model.PactConfig;

public interface PactService {
    void setPactService(PactConfig pactConfig);

    PactConfig getPactConfig();
}
