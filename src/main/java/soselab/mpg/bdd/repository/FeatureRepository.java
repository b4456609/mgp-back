package soselab.mpg.bdd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.bdd.model.Feature;


public interface FeatureRepository extends MongoRepository<Feature, String> {
}
