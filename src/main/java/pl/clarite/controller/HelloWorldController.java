package pl.clarite.controller;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.clarite.service.SystemService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
@RequestScoped
public class HelloWorldController {

    @ConfigProperty(name = "register-service-api/mp-rest/url")
    String registerServiceUrl;

    @Inject
    SystemService systemService;

    @GET
    public String helloWorld() {
        return "Hello world! Register-service-url: " + registerServiceUrl + " serial-n0: " + systemService.getSerialNumber();
    }
}
