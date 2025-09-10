package verticles;

import core.Employee;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DBClientVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(DBClientVerticle.class);


    @Override
    public void start() {
        log.info("Starting DB Client verticle...");
    }

    //TODO implementar llamado a db
    JsonArray queryAllEmployees() {
        Employee employee = new Employee("Carlos", "11111111", "Jefe de seguridad");
        Employee secondEmployee = new Employee("Carlos", "22222222", "Jefe de marketing");
        Employee thirdEmployee = new Employee("Carlos", "33333333", "Jefe de calidad");
        Employee fourthEmployee = new Employee("Carlos", "44444444", "Jefe de producción");
        Employee[] employees = {employee, secondEmployee, thirdEmployee, fourthEmployee};

        return JsonArray.of(employees);
    }

}
