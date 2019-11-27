package org.acme;

import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.reactivex.Flowable;
import io.reactivex.processors.AsyncProcessor;
import io.reactivex.schedulers.Schedulers;


@ApplicationScoped
public class BeerService {

    @RestClient
    BeerGateway beerGateway;

    /**
     * Compare two beers and returns the difference
     */
    public Flowable<JsonValue> compareBeers(String beerA, String beerB) {
        System.out.println("Compare");
        final Flowable<JsonObject> beerAInfo = fromFuture(beerGateway.getBeer(beerA).toCompletableFuture())
                                                .map(array -> array.get(0))
                                                .map(this::filterContent);
                                                
        final Flowable<JsonObject> beerBInfo = fromFuture(beerGateway.getBeer(beerB).toCompletableFuture())
                                                .map(array -> array.get(0))
                                                .map(this::filterContent);

        System.out.println("End Compare");
        return beerAInfo.zipWith(beerBInfo, this::compare);

    }
    
    private JsonObject filterContent(JsonValue beerValue) {
        JsonObject beer = beerValue.asJsonObject();
        return Json.createObjectBuilder()
                    .add("name", beer.getString("name"))
                    .add("abv", beer.getJsonNumber("abv").doubleValue())
                    .add("ibu", beer.getJsonNumber("ibu").doubleValue())
                    .add("ph", beer.getJsonNumber("ph").doubleValue())
                    .build();
    }

    private JsonValue compare(JsonObject beerA, JsonObject beerB) {
        JsonMergePatch jsonMergePatch = Json.createMergeDiff(beerA, beerB);
        return jsonMergePatch.toJsonValue();
    }

    /** Pagination Beers */
    public Flowable<Beer> beers() {
        Flowable<List<Beer>> beerStream = Flowable.generate(() -> 1, (page, emitter) -> {

            System.out.println("Beers from Page " + page);

            final List<Beer> beers = beerGateway.getBeers(page);
            if (beers.isEmpty()) {
                emitter.onComplete();
            } else {
                emitter.onNext(beers);
            }

            return page + 1;
        });

        return beerStream
                .subscribeOn(Schedulers.io())
                .flatMap(Flowable::fromIterable);
    }


    private static <T> Flowable<T> fromFuture(CompletionStage<T> cs) {
        AsyncProcessor<T> ap = AsyncProcessor.create();
        cs.whenComplete((v, e) -> {
            if (e != null) {
                ap.onError(e);
            } else {
                ap.onNext(v);
                ap.onComplete();
            }
        });
        return ap;
    }

}