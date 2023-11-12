package org.github.ehayik.playground;

import lombok.extern.slf4j.Slf4j;
import org.github.ehayik.playground.services.DepartmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner logConnectionDetails(JdbcConnectionDetails jdbc) {
        return args -> {
            var details = STR. """
                    class: \{ jdbc.getClass().getSimpleName() }
                    JDBC URL: \{ jdbc.getJdbcUrl() }
                    Username: \{ jdbc.getUsername() }
                    Password: \{ jdbc.getPassword() }
                    """ ;
            log.info("Database connection details: {}", details);
        };
    }

    @Bean
    CommandLineRunner logDepartmentEmployeesRunner(DepartmentService departmentService) {
        return args -> departmentService.logDepartmentEmployees("Finance");
    }
}
