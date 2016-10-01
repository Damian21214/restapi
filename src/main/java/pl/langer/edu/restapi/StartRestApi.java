package pl.langer.edu.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.langer.edu.restapi.domain.AbstractAuditableEntity;


/**
 * Created by DLanger on 2016-05-12.
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { AbstractAuditableEntity.class, Jsr310JpaConverters.class })
@EnableJpaRepositories(basePackages = "pl.langer.edu.restapi.repositories")
public class StartRestApi {

    public static void main(String[] args) {
        SpringApplication.run(StartRestApi.class, args);
    }
}
