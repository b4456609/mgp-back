package soselab.mpg.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;

/**
 * Created by bernie on 1/11/17.
 */
public interface MdpRepository extends MongoRepository<MicroserviceProjectDescription, String> {
}
