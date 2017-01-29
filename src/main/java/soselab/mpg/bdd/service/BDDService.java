package soselab.mpg.bdd.service;

import java.util.List;

/**
 * Created by bernie on 2017/1/29.
 */
public interface BDDService {
    void parseProject() throws NoBDDProjectGitSettingException;

    void updateGitUrl(String url);

    List<ScenarioWithTagDTO> getScenarioWithTag();
}
