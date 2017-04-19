package soselab.mpg.regression.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.graph.service.GraphService;
import soselab.mpg.mpd.model.IDExtractor;
import soselab.mpg.pact.service.PactService;
import soselab.mpg.regression.controller.dto.ConsumerDetail;
import soselab.mpg.regression.controller.dto.PactTestCaseDTO;
import soselab.mpg.regression.model.ConsumerProviderPair;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ServiceTestStrategy extends AbstractRegressionPicker<PactTestCaseDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTestStrategy.class);
    private final PactService pactService;

    @Autowired
    public ServiceTestStrategy(GraphService graphService, PactService pactService) {
        super(graphService);
        this.pactService = pactService;
    }

    @Override
    protected List<PactTestCaseDTO> getTestCaseResult(List<List<String>> targetPath, String target) {
        HashMap<String, Integer> serviceAndPriority = new HashMap<>();
        targetPath.forEach(path -> {
            //get index of target service
            int targetIndex = getTargetIndex(target, path);

            //generate provider and consumer pair
            for (int i = 1; i < path.size(); i++) {
                String consumerServiceName = IDExtractor.getServiceName(path.get(i - 1));
                String providerServiceName = IDExtractor.getServiceName(path.get(i));

                final int priority = targetIndex - i;
                // if order are smaller replace it in hashmap
                serviceAndPriority.compute(consumerServiceName + "!" + providerServiceName,
                        (k, v) -> v == null ? priority : Math.min(priority, v));

            }
        });

        List<ConsumerProviderPair> serviceTestPair = new ArrayList<>();
        serviceAndPriority.forEach((k, v) -> {
            String[] split = k.split("!");
            String consumer = split[0];
            String provider = split[1];
            serviceTestPair.add(new ConsumerProviderPair(provider, consumer, v));
        });
        serviceTestPair.sort(Comparator.comparingInt(ConsumerProviderPair::getOrder));
        LOGGER.info("service Test regression result: {}", serviceTestPair);

        List<Map<String, List<String>>> urls = pactService.getPactUrlByConsumerAndProvider(serviceTestPair);
        LOGGER.info("urls", urls);

        List<PactTestCaseDTO> pactTestCaseDTOS = new ArrayList<>();
        urls.forEach(url -> {
            for (String s : url.keySet()) {
                List<ConsumerDetail> consumerDetails = url.get(s).stream()
                        .map(link -> new ConsumerDetail(link.split("/")[7], link))
                        .collect(Collectors.toList());
                pactTestCaseDTOS.add(new PactTestCaseDTO(s, consumerDetails));
            }
        });

        return pactTestCaseDTOS;
    }
}
