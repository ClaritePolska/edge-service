package pl.clarite.controller;

import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;
import pl.clarite.service.RegisterService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/register")
@RequestScoped
public class RegisterController {

    @Inject
    RegisterService registerService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterResponse register(RegisterRequest registerRequest) {
        return registerService.register(registerRequest);
    }
}
