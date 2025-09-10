package core;

import interfaces.Endpoints;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Cons;

@ApplicationScoped
public class API extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(API.class);
    @Inject
    private EventBus bus;
    @Inject
    private Endpoints endpoints;

    @Override
    public void start() {
        log.info("Starting Employee API...");
        deployEndpoints();
    }

    private void deployEndpoints() {
        Router router = Router.router(vertx);

        deployGetEndpoints(router);
        deployPostEndpoints(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8081);
    }

    private void deployGetEndpoints(Router router) {
        for (String endpoint : endpoints.get().keySet()) {
            router.get(endpoints.get().get(endpoint))
                .handler(rc -> {
                    String handler = Cons.GET_REQUEST_PREFIX + endpoint;
                    HttpServerRequest request = rc.request();
                    StringBuilder response = new StringBuilder();

                    bus.request(handler, request, ar -> {
                        if (ar.succeeded()) {
                            response.append(ar.result().body().toString());
                        } else {
                            response.append(ar.cause().getMessage());
                        }
                        log.info(response.toString());
                        rc.response().end(response.toString());
                    });
                });
        }
    }

    private void deployPostEndpoints(Router router) {
        for (String endpoint : endpoints.post().keySet()) {
            router.post(endpoints.post().get(endpoint))
                    .handler(rc -> {
                       String handler = Cons.POST_REQUEST_PREFIX + endpoint;
                       HttpServerRequest request = rc.request();
                       StringBuilder response = new StringBuilder();

                       bus.request(handler, request, ar -> {
                           if (ar.succeeded()) {
                               response.append(ar.result().body().toString());
                           } else {
                               response.append(ar.cause().getMessage());
                           }
                           log.info(response.toString());
                           rc.response().end(response.toString());
                       });
                    });
        }
    }


}
