package soselab.mpg.bdd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.bdd.model.Scenario;

import java.util.List;

/**
 * Created by bernie on 2017/1/29.
 */
public interface ScenarioRepository extends MongoRepository<Scenario, String> {
    List<Scenario> findByTagsContaining(String tag);

}
