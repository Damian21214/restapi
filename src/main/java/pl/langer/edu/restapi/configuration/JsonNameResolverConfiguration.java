package pl.langer.edu.restapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.langer.edu.restapi.infrastructure.webhelpers.JsonNameResolver;
import pl.langer.edu.restapi.infrastructure.webhelpers.implementation.JsonNameResolverJacksonImpl;

/**
 * Created by Damian Langer on 01.10.16.
 */
@Configuration
public class JsonNameResolverConfiguration {
    @Bean
    JsonNameResolver jsonNameResolver() {
        return new JsonNameResolverJacksonImpl();
    }
}
