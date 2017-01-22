package soselab.mpg.pact.service;

import soselab.mpg.pact.model.Pact;
import soselab.mpg.pact.model.PactConfig;

import java.util.List;

public interface PactService {
    void setPactService(PactConfig pactConfig);

    PactConfig getPactConfig();

    void getLatestPactFile();

    List<Pact> getPacts();
}
