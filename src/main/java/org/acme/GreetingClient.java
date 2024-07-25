package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(baseUri = "http://localhost:8080/hello")
public interface GreetingClient {

  @GET
  Response sampleGet();
  @GET
  @Path("list")
  Response sampleGetList();
}
