package org.acme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.common.jaxrs.ResponseImpl;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    @RestClient
    GreetingClient client;

    @GET
    public SampleRecord hello() {
        return new SampleRecord("Hello from RESTEasy Reactive", System.currentTimeMillis() % 10);
    }

    @GET
    @Path("proxy")
    public Response proxy() {
        return Response.ok(client.sampleGet().getEntity()).build();
    }

    @GET
    @Path("proxy-with-casting")
    public Response proxyWithCasting() {
        return Response.ok(client.sampleGet().readEntity(SampleRecord.class)).build();
    }

    @GET
    @Path("proxy-with-stream")
    public Response proxyStream() throws IOException {
        try (BufferedReader r = new BufferedReader(
                new InputStreamReader(((ResponseImpl) client.sampleGet()).getEntityStream(), StandardCharsets.UTF_8))) {
            return Response.ok(r.lines()
                    .collect(Collectors.joining("\n"))).build();
        }
    }

    public record SampleRecord(String message, long someNumber){}
}
