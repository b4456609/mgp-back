package soselab.mpg.pact.service;

import soselab.mpg.pact.model.ServiceCallRelationInformation;

import java.util.List;

public interface PactService {
    void updatePactUrl(String url);

    String getPactGitUrl();

    void getLatestPactFile();

    List<ServiceCallRelationInformation> getPacts();
}
