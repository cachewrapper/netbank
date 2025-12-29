package org.cachewrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.cachewrapper")
@ConfigurationPropertiesScan(basePackages = "org.cachewrapper")
public class WriteBackendSpringApplication {

    static void main(String[] args) {
        SpringApplication.run(WriteBackendSpringApplication.class, args);
    }
}