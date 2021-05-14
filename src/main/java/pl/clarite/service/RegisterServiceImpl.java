package pl.clarite.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import pl.clarite.client.RegisterServiceClient;
import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RegisterServiceImpl {

    private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

    @Inject
    @RestClient
    RegisterServiceClient registerServiceClient;

    @Inject
    SystemService systemService;

    @PostConstruct
    public void register() {
        try {
            String serial = systemService
                    .getSerialNumber()
                    .orElseThrow(() -> new RuntimeException("Could not register edge device in the DataHub."));

            RegisterResponse registerResponse = registerServiceClient.register(RegisterRequest.builder()
                    .serial(serial)
                    .name("Clarite Polska S.A.")
                    .latitude(12.5)
                    .longitude(12.5)
                    .build());
        } catch (Exception ex) {
            logger.error("Could not register at the DataHub.");
            throw new IllegalStateException("Could not register at the DataHub.");
        }
    }

    // TODO: implement unregister functionality
    /*@PreDestroy
    public void unregister() {

    }*/
}
