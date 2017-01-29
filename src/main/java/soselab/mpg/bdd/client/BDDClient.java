package soselab.mpg.bdd.client;

import soselab.mpg.bdd.client.dto.FeatureDTO;
import soselab.mpg.bdd.client.dto.LatestCommitStatusDTO;

import java.util.List;

/**
 * Created by bernie on 2017/1/29.
 */
public interface BDDClient {
    LatestCommitStatusDTO gitClone(String url);

    LatestCommitStatusDTO pull();

    LatestCommitStatusDTO getLastestCommitStatus();

    List<FeatureDTO> getParseData();
}
