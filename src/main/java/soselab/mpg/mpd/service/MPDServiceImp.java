package soselab.mpg.mpd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import soselab.mpg.mpd.model.MicroserviceProjectDescription;
import soselab.mpg.mpd.model.ServiceName;
import soselab.mpg.mpd.repository.MicroserviceProjectDescriptionRepository;
import soselab.mpg.mpd.repository.ServiceNameRepository;
import soselab.mpg.mpd.service.exception.MicroserviceProjectDescriptionDeserializeException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MPDServiceImp implements MPDService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MPDServiceImp.class);

    private final MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository;
    private final MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader;
    private final ServiceNameRepository serviceNameRepository;

    @Autowired
    public MPDServiceImp(MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository,
                         MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader,
                         ServiceNameRepository serviceNameRepository) {
        this.microserviceProjectDescriptionRepository = microserviceProjectDescriptionRepository;
        this.microserviceProjectDescriptionReader = microserviceProjectDescriptionReader;
        this.serviceNameRepository = serviceNameRepository;
    }

    @Override
    public void uploadFile(String json) {
        MicroserviceProjectDescription mdp = microserviceProjectDescriptionReader
                .readMDP(json)
                .orElseThrow(MicroserviceProjectDescriptionDeserializeException::new);

        microserviceProjectDescriptionRepository.save(mdp);
        try {
            serviceNameRepository.save(new ServiceName(mdp.getName()));
        } catch (DuplicateKeyException e) {
            LOGGER.info("duplicate service key");
        }
    }

    @Override
    public List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions() {

        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        LOGGER.info("all service names {}", serviceNames.toString());

        return serviceNames.stream().map(serviceName -> microserviceProjectDescriptionRepository
                .findFirstByNameOrderByTimestampDesc(serviceName.getServiceName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getServiceSwagger(String serviceName) {
        return microserviceProjectDescriptionRepository.findFirstByNameOrderByTimestampDesc(serviceName).getSwagger();
    }

}
