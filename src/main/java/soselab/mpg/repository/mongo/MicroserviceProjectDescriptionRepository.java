package soselab.mpg.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;

/**
 * Created by bernie on 1/11/17.
 */
public interface MicroserviceProjectDescriptionRepository extends MongoRepository<MicroserviceProjectDescription, String> {
}
