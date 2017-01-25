package soselab.mpg.pact.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.pact.model.ServiceCallRelationInformation;

public interface PactRepository extends MongoRepository<ServiceCallRelationInformation, String> {
}
