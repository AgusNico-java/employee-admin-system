package verticles;

import core.Employee;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Cons;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class EmployeeVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(EmployeeVerticle.class);
    @Inject
    private EventBus bus;
    @Inject
    private DBClientVerticle client;

    @Override
    public void start() {
        log.info("Starting Employee verticle...");
    }

    //TODO: implementar correctamente cada método
    @ConsumeEvent(Cons.EV_LIST_EMPLOYEES_HANDLER)
    public CompletionStage<String> listEmployees(JsonObject request) {
        CompletableFuture<String> future = new CompletableFuture<>();

        return future;

    }

    @ConsumeEvent(Cons.EV_SPECIFIC_EMPLOYEE_HANDLER)
    public CompletionStage<String> getEmployee(JsonObject request) {
        CompletableFuture<String> future = new CompletableFuture<>();
        int employeeId = request.getInteger("id");

        bus.request(Cons.DBV_GET_EMPLOYEE_HANDLER, employeeId, ar -> {
            if (ar.succeeded()) {
                future.complete(ar.result().body().toString());
            } else {
                future.completeExceptionally(ar.cause());
            }
        });

        return future;
    }

    @ConsumeEvent(Cons.EV_GET_EMPLOYEES_INFO_HANDLER)
    public CompletionStage<String> employeesInfo(JsonObject request) {
        CompletableFuture<String> future = new CompletableFuture<>();

        bus.request(Cons.DBV_GET_ALL_EMPLOYEES_HANDLER, request, ar -> {
            if (ar.succeeded()) {
                future.complete(ar.result().body().toString());
            } else {
                future.completeExceptionally(ar.cause());
            }
        });

        return future;

    }

    @ConsumeEvent(Cons.EV_CREATE_EMPLOYEE_HANDLER)
    public CompletionStage<String> insertEmployee(JsonObject request) {
        CompletableFuture<String> future = new CompletableFuture<>();

        Employee employeeToInsert = request.mapTo(Employee.class);
        bus.request(Cons.DBV_INSERT_EMPLOYEE__HANDLER, employeeToInsert, ar -> {
            if (ar.succeeded()) {
                future.complete(ar.result().body().toString());
            } else {
                future.completeExceptionally(ar.cause());
            }
        });
        return future;
    }

    //TODO: Abstraer request del event bus de los llamados a db

}
