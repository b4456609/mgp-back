package soselab.mpg;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import soselab.mpg.pact.repository.PactRepository;
import soselab.mpg.repository.mongo.MicroserviceProjectDescriptionRepository;
import soselab.mpg.repository.neo4j.ServiceNodeRepository;

@EnableMongoRepositories(basePackageClasses = {MicroserviceProjectDescriptionRepository.class, PactRepository.class})
@EnableNeo4jRepositories(basePackageClasses = ServiceNodeRepository.class)
@SpringBootApplication
public class MpgbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpgbackApplication.class, args);
	}

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
