package pl.clarite.service;

import io.quarkus.runtime.Startup;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import pl.clarite.client.RegisterServiceClient;
import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@Startup
@ApplicationScoped
public class RegisterServiceImpl {

    private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

    @Inject
    @RestClient
    RegisterServiceClient registerServiceClient;

    @Inject
    SystemService systemService;

    @ConfigProperty(name = "qiot.team.name")
    String qiotTeamName;

    @ConfigProperty(name = "qiot.team.latitude")
    String qiotTeamLatitude;

    @ConfigProperty(name = "qiot.team.longitude")
    String qiotTeamLongitude;

    @PostConstruct
    public void register() {
        try {
            String serial = systemService
                    .getSerialNumber()
                    .orElseThrow(() -> new RuntimeException("Could not register edge device in the DataHub."));

            RegisterResponse registerResponse = registerServiceClient.register(RegisterRequest.builder()
                    .serial(serial + "_____" + UUID.randomUUID())
                    .name(qiotTeamName)
                    .latitude(Double.parseDouble(qiotTeamLatitude))
                    .longitude(Double.parseDouble(qiotTeamLongitude))
                    .build());

            logger.info("=======================================================================");
            logger.info(registerResponse);
            logger.info("=======================================================================");

            systemService.saveEdgeDetails(registerResponse);
            systemService.saveEdgeKeystore(registerResponse.getKeystore());
            systemService.saveEdgeTruststore(registerResponse.getTruststore());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Could not register the edge device in the DataHub network.");
            throw new IllegalStateException("Could not register the edge device in the DataHub network.");
        }
    }

    // TODO: implement unregister functionality
    /*@PreDestroy
    public void unregister() {

    }*/
}
