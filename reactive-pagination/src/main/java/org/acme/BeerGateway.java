package org.acme;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.json.JsonArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v2")
@RegisterRestClient
public interface BeerGateway {

    @GET
    @Path("/beers")
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name="user-agent", value="curl/7.54.0")
    List<Beer> getBeers(@QueryParam("page") int page);

    @GET
    @Path("/beers")
    @Produces(MediaType.APPLICATION_JSON)
    CompletionStage<JsonArray> getBeer(@QueryParam("beer_name") String name);
}