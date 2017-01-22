package soselab.mpg.pact.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.pact.model.Pact;

public interface PactRepository extends MongoRepository<Pact, String> {
}
