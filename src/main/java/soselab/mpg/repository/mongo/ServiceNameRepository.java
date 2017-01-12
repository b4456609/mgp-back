package soselab.mpg.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.model.ServiceName;

/**
 * Created by bernie on 2017/1/12.
 */
public interface ServiceNameRepository extends MongoRepository<ServiceName, String> {
}
