package soselab.mpg.graph.service.handler;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.ScenarioWithTagDTO;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ScenarioNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ScenarioNodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScenarioBuildHandler implements GraphBuildHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioBuildHandler.class);
    @Autowired
    BDDService bddService;

    @Autowired
    EndpointNodeRepository endpointNodeRepository;

    @Autowired
    ScenarioNodeRepository scenarioNodeRepository;

    @Override
    public void build() {
        Iterable<EndpointNode> all = endpointNodeRepository.findAll();
        List<EndpointNode> endpointNodes = IteratorUtils.toList(all.iterator());

        List<ScenarioNode> scenarioNodes = new ArrayList<>();
        List<ScenarioWithTagDTO> scenarioWithTagDTOS = bddService.getScenarioWithTag();
        for (ScenarioWithTagDTO scenarioWithTagDTO : scenarioWithTagDTOS) {
            // the scenario's tag which is endpoint id
            Set<String> tags = scenarioWithTagDTO.getTags();
            LOGGER.info("tags {}", tags);

            // get the endpoint node with the this scenario
            Set<EndpointNode> collect = endpointNodes.stream()
                    .filter(endpointNode -> {
                        LOGGER.debug("{} is in tags: {}", endpointNode.getEndpointId(), tags.contains(endpointNode.getEndpointId()));
                        return tags.contains(endpointNode.getEndpointId());
                    })
                    .collect(Collectors.toSet());
            LOGGER.info("collect EndpointNode {}", collect);

            // build scenario node
            String id = scenarioWithTagDTO.getId();
            String name = scenarioWithTagDTO.getName();
            ScenarioNode scenarioNode = new ScenarioNode(name, id, collect);
            scenarioNodes.add(scenarioNode);
        }
        LOGGER.info("{}", scenarioNodes);
        scenarioNodeRepository.save(scenarioNodes);
    }
}
