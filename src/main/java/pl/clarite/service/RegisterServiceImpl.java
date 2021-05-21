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
public class RegisterServiceImpl implements RegisterService {

    private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

    @Inject
    @RestClient
    RegisterServiceClient registerServiceClient;

    @Inject
    SystemService systemService;

    @PostConstruct
    public void asciArt() {
        System.out.println("   ******   **                  **   **             *******            **         **                  ********        **       \n" +
                "  **////** /**                 //   /**            /**////**          /**        /**                 **//////        ****      \n" +
                " **    //  /**  ******   ****** ** ******  *****   /**   /**  ******  /**  ******/**  **  ******    /**             **//**     \n" +
                "/**        /** //////** //**//*/**///**/  **///**  /*******  **////** /** **//// /** **  //////**   /*********     **  //**    \n" +
                "/**        /**  *******  /** / /**  /**  /*******  /**////  /**   /** /**//***** /****    *******   ////////**    **********   \n" +
                "//**    ** /** **////**  /**   /**  /**  /**////   /**      /**   /** /** /////**/**/**  **////**          /** **/**//////** **\n" +
                " //******  ***//********/***   /**  //** //******  /**      //******  *** ****** /**//**//********   ******** /**/**     /**/**\n" +
                "  //////  ///  //////// ///    //    //   //////   //        //////  /// //////  //  //  ////////   ////////  // //      // //");
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        try {
            RegisterResponse registerResponse = registerServiceClient.register(registerRequest);

            systemService.saveEdgeDetails(registerResponse);
            systemService.saveEdgeKeystore(registerResponse.getKeystore());
            systemService.saveEdgeTruststore(registerResponse.getTruststore());

            System.setProperty("STATION_ID", registerResponse.getId());
            System.setProperty("KEYSTORE_PASSWORD", registerRequest.getKeyStorePassword());

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
