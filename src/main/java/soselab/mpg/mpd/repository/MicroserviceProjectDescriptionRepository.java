package soselab.mpg.mpd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;

/**
 * Created by bernie on 1/11/17.
 */
public interface MicroserviceProjectDescriptionRepository extends MongoRepository<MicroserviceProjectDescription, String> {
    MicroserviceProjectDescription findFirstByNameOrderByTimestampAsc(String lastname);
}
