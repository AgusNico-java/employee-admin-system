package verticles;

import core.Employee;
import interfaces.DBConnection;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Cons;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DBClientVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(DBClientVerticle.class);

    MySQLConnectOptions connectOptions = new MySQLConnectOptions();
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    Pool pool;

    @Inject
    DBConnection dbProperties;

    @Override
    public void start() {
        log.info("Starting DB Client verticle...");
        this.connectOptions
                .setPort(dbProperties.port())
                .setHost(dbProperties.host())
                .setDatabase(dbProperties.db())
                .setUser(dbProperties.user())
                .setPassword(dbProperties.password());

        pool = startPool();
    }


    @ConsumeEvent(Cons.DBV_INSERT_EMPLOYEE__HANDLER)
    public CompletionStage<String> insertEmployee(Employee employee) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String successMessage = "Employee %s created successfully";

        pool
                .preparedQuery(Cons.INSERT_EMPLOYEE_QUERY)
                .execute(Tuple.of(
                                employee.id,
                                employee.name,
                                employee.role,
                                employee.employmentDate,
                                employee.birthdayDate,
                                employee.isAdmin,
                                employee.canManageFood
                        )
                )
                .onComplete(ar -> future.complete(String.format(successMessage, employee.name)))
                .onFailure(future::completeExceptionally);

        return future;
    }

    @ConsumeEvent(Cons.DBV_GET_EMPLOYEE_HANDLER)
    public CompletionStage<String> getEmployee(int employeeId) {
        CompletableFuture<String> future = new CompletableFuture<>();

        pool.preparedQuery(Cons.GET_EMPLOYEE_QUERY)
                .execute(Tuple.of(employeeId))
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        RowSet<Row> rows = ar.result();
                        for (Row row : rows) {
                            future.complete(row.toJson().encodePrettily());
                            log.info(row.toJson().encodePrettily());
                        }
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }

    @ConsumeEvent(Cons.DBV_GET_ALL_EMPLOYEES_HANDLER)
    public CompletionStage<JsonArray> getAllEmployees(JsonObject request) {
        CompletableFuture<JsonArray> future = new CompletableFuture<>();
        JsonArray employees = new JsonArray();

        pool.query(Cons.GET_EMPLOYEES_QUERY)
                .execute()
                .onComplete(ar -> {
                   if (ar.succeeded()) {
                       RowSet<Row> rows = ar.result();
                       for (Row row : rows) {
                           employees.add(row.toJson());
                       }
                       future.complete(employees);
                   } else {
                       future.completeExceptionally(ar.cause());
                   }
                });

        return future;
    }

    private Pool startPool() {
        return MySQLBuilder.pool()
                .with(this.poolOptions)
                .connectingTo(connectOptions)
                .using(Vertx.vertx())
                .build();
    }


}
