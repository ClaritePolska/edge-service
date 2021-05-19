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

//@Startup
@ApplicationScoped
public class RegisterServiceImpl implements RegisterService {

    private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

    @Inject
    @RestClient
    RegisterServiceClient registerServiceClient;

    @Inject
    SystemService systemService;

    @ConfigProperty(name = "qiot.station.name")
    String qiotTeamName;

    @ConfigProperty(name = "qiot.station.latitude")
    String qiotTeamLatitude;

    @ConfigProperty(name = "qiot.station.longitude")
    String qiotTeamLongitude;

    @PostConstruct
    public void register() {
        System.out.println("   ******   **                  **   **             *******            **         **                  ********        **       \n" +
                "  **////** /**                 //   /**            /**////**          /**        /**                 **//////        ****      \n" +
                " **    //  /**  ******   ****** ** ******  *****   /**   /**  ******  /**  ******/**  **  ******    /**             **//**     \n" +
                "/**        /** //////** //**//*/**///**/  **///**  /*******  **////** /** **//// /** **  //////**   /*********     **  //**    \n" +
                "/**        /**  *******  /** / /**  /**  /*******  /**////  /**   /** /**//***** /****    *******   ////////**    **********   \n" +
                "//**    ** /** **////**  /**   /**  /**  /**////   /**      /**   /** /** /////**/**/**  **////**          /** **/**//////** **\n" +
                " //******  ***//********/***   /**  //** //******  /**      //******  *** ****** /**//**//********   ******** /**/**     /**/**\n" +
                "  //////  ///  //////// ///    //    //   //////   //        //////  /// //////  //  //  ////////   ////////  // //      // //");

        /*try {
            String serial = systemService
                    .getSerialNumber();

            RegisterResponse registerResponse = registerServiceClient.register(RegisterRequest.builder()
                    .serial(serial + "_____" + UUID.randomUUID())
                    .name(qiotTeamName)
                    .latitude(Double.parseDouble(qiotTeamLatitude))
                    .longitude(Double.parseDouble(qiotTeamLongitude))
                    .build());

            systemService.saveEdgeDetails(registerResponse);
            systemService.saveEdgeKeystore(registerResponse.getKeystore());
            systemService.saveEdgeTruststore(registerResponse.getTruststore());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Could not register the edge device in the DataHub network.");
            throw new RuntimeException("Could not register the edge device in the DataHub network.");
        }*/
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        try {
            RegisterResponse registerResponse = registerServiceClient.register(registerRequest);

            systemService.saveEdgeDetails(registerResponse);
            systemService.saveEdgeKeystore(registerResponse.getKeystore());
            systemService.saveEdgeTruststore(registerResponse.getTruststore());

            return registerResponse;
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