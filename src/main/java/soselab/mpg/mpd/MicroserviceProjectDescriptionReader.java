package soselab.mpg.mpd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soselab.mpg.model.mpd.MicroserviceProjectDescription;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
public class MicroserviceProjectDescriptionReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceProjectDescriptionReader.class);

    private final ObjectMapper objectMapper;
    private final Validator validator;

    public MicroserviceProjectDescriptionReader() {
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,true)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Optional<MicroserviceProjectDescription> readMDP(String mdpJson) {
        try {
            MicroserviceProjectDescription mdp = objectMapper.readValue(mdpJson, MicroserviceProjectDescription.class);
            mdp.setId(mdp.getTimestamp()+mdp.getName());
            Set<ConstraintViolation<MicroserviceProjectDescription>> validate = validator.validate(mdp);
            if(!validate.isEmpty())
                throw new IllegalArgumentException(validate.toString());
            return Optional.of(mdp);
        } catch (IOException e) {
            LOGGER.error("Can't not read json");
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
