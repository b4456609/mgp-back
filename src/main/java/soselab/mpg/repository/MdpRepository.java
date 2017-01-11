package soselab.mpg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.mpd.model.Mdp;

/**
 * Created by bernie on 1/11/17.
 */
public interface MdpRepository extends MongoRepository<Mdp,String>{
}
