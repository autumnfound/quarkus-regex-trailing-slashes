package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/hello")
public class GreetingResource {

    @GET
    @Path("working/{sample}")
    public String hello(int sample) {
        Log.infof("Was sent the number: %d", new Object[] { sample });
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("non-working/{sample:\\d+}")
    public String helloWithRegex(int sample) {
        Log.infof("Was sent the number: %d", new Object[] { sample });
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("non-working/{sample:\\d+}/second-test")
    public String helloWithRegexSecond(int sample) {
        Log.infof("Was sent the number: %d", new Object[] { sample });
        return "Hello from RESTEasy Reactive";
    }

}
