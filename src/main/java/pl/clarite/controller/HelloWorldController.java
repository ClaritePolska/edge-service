package pl.clarite.controller;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
@RequestScoped
public class HelloWorldController {

    @ConfigProperty(name = "register-service-api/mp-rest/url")
    String registerServiceUrl;

    @GET
    public String helloWorld() {
        return "Hello world! Register-service-url: " + registerServiceUrl;
    }
}
