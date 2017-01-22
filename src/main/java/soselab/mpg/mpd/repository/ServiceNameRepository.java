package soselab.mpg.mpd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.mpd.model.ServiceName;

/**
 * Created by bernie on 2017/1/12.
 */
public interface ServiceNameRepository extends MongoRepository<ServiceName, String> {
}
