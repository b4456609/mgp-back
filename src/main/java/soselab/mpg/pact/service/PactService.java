package soselab.mpg.pact.service;

import soselab.mpg.pact.model.PactConfig;
import soselab.mpg.pact.model.ServiceCallRelationInformation;

import java.util.List;

public interface PactService {
    void setPactService(PactConfig pactConfig);

    PactConfig getPactConfig();

    void getLatestPactFile();

    List<ServiceCallRelationInformation> getPacts();
}
