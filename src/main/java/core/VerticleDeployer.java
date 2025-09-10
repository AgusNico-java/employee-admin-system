package core;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class VerticleDeployer {
    private static final Logger log = LoggerFactory.getLogger(VerticleDeployer.class);

    @ConfigProperty(name = "quarkus.application.name")
    String appName;
    @ConfigProperty(name = "quarkus.application.version")
    String appVersion;

    public void init(@Observes StartupEvent start, Vertx vertx, Instance<AbstractVerticle> verticles) {
        log.info(String.format("Starting application: %s v%s", appName, appVersion));

        //TODO: once the first application, abstract the deployment steps into a utils library
        try {
            for (AbstractVerticle verticle : verticles) {
                log.info("Deploying verticle: " + verticle.getClass().getName());
                vertx.deployVerticle(verticle, ar -> {
                   if (ar.failed()) {
                       log.error("Verticle deployment failed. Canceling system start...\n" + ar.cause());
                       ar.cause().printStackTrace();
                   }
                });
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            Quarkus.asyncExit();
        }
    }
}
