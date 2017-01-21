package soselab.mpg.pact.client;

public interface PactClient {
    String getPactFileByProviderAndConsumer(String pactUrl, String provider, String consumer);
}
