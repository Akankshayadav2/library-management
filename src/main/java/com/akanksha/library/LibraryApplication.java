package com.akanksha.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the application.
 *
 * @SpringBootApplication = 3 annotations in one:
 *   - @Configuration       : this class can define Spring beans
 *   - @EnableAutoConfiguration : Spring Boot configures things automatically
 *                                (Tomcat, DataSource, JPA...) based on our dependencies
 *   - @ComponentScan       : find all @Controller/@Service/@Repository classes
 *                            in this package and its sub-packages
 */
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        // Starts Spring, creates all beans, and starts the embedded Tomcat on port 8080
        SpringApplication.run(LibraryApplication.class, args);
    }
}
