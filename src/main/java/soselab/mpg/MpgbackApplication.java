package soselab.mpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import soselab.mpg.repository.mongo.MdpRepository;
import soselab.mpg.repository.neo4j.ServiceRepository;

@EnableMongoRepositories(basePackageClasses = MdpRepository.class)
@EnableNeo4jRepositories(basePackageClasses = ServiceRepository.class)
@SpringBootApplication
public class MpgbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpgbackApplication.class, args);
	}
}
