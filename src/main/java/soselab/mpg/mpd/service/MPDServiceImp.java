package soselab.mpg.mpd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MPDServiceImp(MicroserviceProjectDescriptionRepository microserviceProjectDescriptionRepository, MicroserviceProjectDescriptionReader microserviceProjectDescriptionReader, ServiceNameRepository serviceNameRepository) {
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
        serviceNameRepository.save(new ServiceName(mdp.getName(), mdp.getName()));
    }

    @Override
    public List<MicroserviceProjectDescription> getMicroserviceProjectDescriptions() {

        List<ServiceName> serviceNames = serviceNameRepository.findAll();
        LOGGER.info("all service names {}", serviceNames.toString());

        return serviceNames.stream().map(serviceName -> microserviceProjectDescriptionRepository
                .findFirstByNameOrderByTimestampAsc(serviceName.getServiceName()))
                .collect(Collectors.toList());
    }
}
