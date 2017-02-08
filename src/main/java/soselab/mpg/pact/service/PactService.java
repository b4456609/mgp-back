package soselab.mpg.pact.service;

import soselab.mpg.pact.model.ServiceCallRelationInformation;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.util.List;

public interface PactService {
    void updatePactUrl(String url);

    String getPactGitUrl();

    void getLatestPactFile();

    List<ServiceCallRelationInformation> getPacts();

    List<String> getPactUrlByConsumerAndProvider(List<ConsumerProviderPair> serviceTestPair);
}
