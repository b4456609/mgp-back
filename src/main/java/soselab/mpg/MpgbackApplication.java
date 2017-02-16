package soselab.mpg;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import soselab.mpg.bdd.repository.ScenarioRepository;
import soselab.mpg.graph.repository.ServiceNodeRepository;
import soselab.mpg.mpd.repository.MicroserviceProjectDescriptionRepository;
import soselab.mpg.pact.repository.PactConfigRepository;
import soselab.mpg.testreader.repository.TestReportRepository;

@EnableMongoRepositories(basePackageClasses = {MicroserviceProjectDescriptionRepository.class, PactConfigRepository.class, ScenarioRepository.class, TestReportRepository.class})
@EnableNeo4jRepositories(basePackageClasses = ServiceNodeRepository.class)
@SpringBootApplication
@EnableMongoAuditing
public class MpgbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpgbackApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
