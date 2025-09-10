package verticles;

import core.Employee;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
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
    @ConsumeEvent(Cons.LIST_EMPLOYEES_HANDLER)
    public CompletionStage<String> listEmployees(HttpServerRequest request) {
        CompletableFuture<String> future = new CompletableFuture<>();

        JsonArray employeeArray = client.queryAllEmployees();
        future.complete(employeeArray.encodePrettily());
        return future;

    }

    @ConsumeEvent(Cons.SPECIFIC_EMPLOYEE_HANDLER)
    public CompletionStage<String> getEmployee(HttpServerRequest request) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Info de empleado correcta");
        return future;

    }

    @ConsumeEvent(Cons.GET_EMPLOYEES_INFO_HANDLER)
    public CompletionStage<String> employeesInfo(HttpServerRequest request) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Info de todos los empleados correcta");
        return future;

    }


}
