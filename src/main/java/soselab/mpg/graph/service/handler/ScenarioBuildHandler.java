package soselab.mpg.graph.service.handler;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.bdd.model.ScenarioTagUtil;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.ScenarioWithTagDTO;
import soselab.mpg.graph.model.EndpointNode;
import soselab.mpg.graph.model.ScenarioNode;
import soselab.mpg.graph.repository.EndpointNodeRepository;
import soselab.mpg.graph.repository.ScenarioNodeRepository;

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
        //remove old scenario node
        scenarioNodeRepository.deleteAll();

        //get all endpoint node in neo4j for relationship
        Iterable<EndpointNode> all = endpointNodeRepository.findAll();
        List<EndpointNode> endpointNodes = IteratorUtils.toList(all.iterator());

        List<ScenarioWithTagDTO> scenarioWithTagDTOS = bddService.getScenarioWithTag();
        List<ScenarioNode> scenarioNodes = scenarioWithTagDTOS.stream()
                .map(scenarioWithTagDTO -> {
                    // the scenario's tag which is endpoint id
                    Set<String> tags = scenarioWithTagDTO.getTags().stream()
                            .map(ScenarioTagUtil::translateToEndpointId)
                            .collect(Collectors.toSet());
                    LOGGER.info("tags {}", tags);

                    // get the endpoint node with the this scenario
                    Set<EndpointNode> collect = endpointNodes.stream()
                            .filter(endpointNode -> {
                                boolean contains = tags.contains(endpointNode.getEndpointId());
                                LOGGER.debug("{} is in tags: {}", endpointNode.getEndpointId(),
                                        contains);
                                return contains;
                            })
                            .collect(Collectors.toSet());
                    LOGGER.info("collect EndpointNode {}", collect);

                    // build scenario node
                    String id = scenarioWithTagDTO.getId();
                    String name = scenarioWithTagDTO.getName();
                    ScenarioNode scenarioNode = new ScenarioNode(name, id, collect);
                    return scenarioNode;
                }).collect(Collectors.toList());
        LOGGER.info("{}", scenarioNodes);
        scenarioNodeRepository.save(scenarioNodes);
    }
}
