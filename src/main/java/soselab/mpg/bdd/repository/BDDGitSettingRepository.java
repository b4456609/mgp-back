package soselab.mpg.bdd.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.bdd.model.BDDGitSetting;


public interface BDDGitSettingRepository extends MongoRepository<BDDGitSetting, ObjectId> {
}
