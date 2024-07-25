package org.acme;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.common.jaxrs.ResponseImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    @RestClient
    GreetingClient client;
    @Inject
    ObjectMapper m;

    /*
     * Object-based responses
     */

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
        try (InputStream is = ((ResponseImpl) client.sampleGet()).getEntityStream()) {
            return Response.ok(m.readValue(is, SampleRecord.class)).build();
        }
    }

    /*
     * List-based responses
     */

    @GET
    @Path("list")
    public List<SampleRecord> hellos() {
        return Arrays.asList(new SampleRecord("Hello from RESTEasy Reactive", System.currentTimeMillis() % 10));
    }

    @GET
    @Path("list/proxy")
    public Response proxyList() {
        return Response.ok(client.sampleGetList().getEntity()).build();
    }

    @GET
    @Path("list/proxy-with-casting")
    public Response proxyListCast() {
        return Response.ok(client.sampleGetList().readEntity((Class<List<SampleRecord>>) (Class) List.class)).build();
    }

    @GET
    @Path("list/proxy-with-stream")
    public Response proxyListStream() throws IOException {
        try (InputStream is = ((ResponseImpl) client.sampleGetList()).getEntityStream()) {
            return Response.ok(m.readerForListOf(SampleRecord.class).readValue(is)).build();
        }
    }

    public record SampleRecord(String message, long someNumber) {
    }
}
