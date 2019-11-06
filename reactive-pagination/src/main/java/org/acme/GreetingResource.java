package org.acme;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.reactivestreams.Publisher;

import io.reactivex.Single;

@Path("/beers")
public class GreetingResource {

    @RestClient
    BeerGateway beerGateway;

    @Inject
    BeerService beerservice;

    @GET
    @Path("/firstpage")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Beer> getFirstPageBeers() {
        return beerGateway.getBeers(1);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<Beer> getBeers() {
        return beerservice.beers()
            .filter(b -> b.getAbv() > 10.0).take(10);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<Long> countBeers() {
        return beerservice.beers()
            .filter(b -> b.getAbv() > 10.0)
            .count();
    }

    @GET
    @Path("/compare/{beerA}/{beerB}")
    @Produces(MediaType.APPLICATION_JSON)
    public Publisher<JsonValue> compareBeers(@PathParam("beerA") String beerA, 
                                                    @PathParam("beerB") String beerB) {
        return beerservice.compareBeers(beerA, beerB);
    }
}