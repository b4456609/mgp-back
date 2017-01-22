package soselab.mpg.pact.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.pact.model.PactConfig;


public interface PactConfigRepository extends MongoRepository<PactConfig, String> {
}
