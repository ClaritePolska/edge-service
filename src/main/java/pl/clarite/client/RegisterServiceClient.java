package pl.clarite.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/register")
@RegisterRestClient(configKey = "register-service-api")
public interface RegisterServiceClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    RegisterResponse register(RegisterRequest registerRequest);
}
