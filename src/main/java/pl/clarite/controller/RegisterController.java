package pl.clarite.controller;

import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;
import pl.clarite.dto.SerialNumberResponseDto;
import pl.clarite.service.RegisterService;
import pl.clarite.service.SystemService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/")
@RequestScoped
public class RegisterController {

    @Inject
    SystemService systemService;

    @Inject
    RegisterService registerService;

    @GET
    @Path("/serial-number")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SerialNumberResponseDto get() {
        String serialNumber = systemService
                .getSerialNumber();

        return SerialNumberResponseDto.builder()
                .serialNumber(serialNumber)
                .build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterResponse register(RegisterRequest registerRequest) {
        return registerService.register(registerRequest);
    }
}
