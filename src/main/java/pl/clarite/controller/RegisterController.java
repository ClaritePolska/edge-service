package pl.clarite.controller;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import pl.clarite.client.RegisterServiceClient;
import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/register")
@RequestScoped
public class RegisterController {

    @Inject
    @RestClient
    RegisterServiceClient registerServiceClient;

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterResponse register(RegisterRequest registerRequest) {
        return registerServiceClient.register(registerRequest);
    }
}
