package soselab.mpg.graph.service.handler;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import soselab.mpg.bdd.service.BDDService;
import soselab.mpg.bdd.service.NoBDDProjectGitSettingException;
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

    @Autowired
    BDDService bddService;

    @Autowired
    EndpointNodeRepository endpointNodeRepository;

    @Autowired
    ScenarioNodeRepository scenarioNodeRepository;

    @Override
    public void build() {
        try {
            bddService.parseProject();
            Iterable<EndpointNode> all = endpointNodeRepository.findAll();
            List<EndpointNode> endpointNodes = IteratorUtils.toList(all.iterator());

            List<ScenarioNode> scenarioNodes = new ArrayList<>();
            List<ScenarioWithTagDTO> scenarioWithTagDTOS = bddService.getScenarioWithTag();
            for (ScenarioWithTagDTO scenarioWithTagDTO : scenarioWithTagDTOS) {
                String id = scenarioWithTagDTO.getId();
                String name = scenarioWithTagDTO.getName();
                Set<String> tags = scenarioWithTagDTO.getTags();
                Set<EndpointNode> collect = endpointNodes.stream()
                        .filter(endpointNode -> tags.contains(endpointNode.getEndpointId()))
                        .collect(Collectors.toSet());
                ScenarioNode scenarioNode = new ScenarioNode(name, id, collect);
                scenarioNodes.add(scenarioNode);
            }
            scenarioNodeRepository.save(scenarioNodes);
        } catch (NoBDDProjectGitSettingException e) {
            e.printStackTrace();
        }
    }
}
