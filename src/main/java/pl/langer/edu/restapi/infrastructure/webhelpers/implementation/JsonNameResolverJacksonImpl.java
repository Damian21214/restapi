package pl.langer.edu.restapi.infrastructure.webhelpers.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.langer.edu.restapi.infrastructure.webhelpers.JsonNameResolver;

import java.lang.reflect.Field;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

/**
 * Created by Damian Langer on 01.10.16.
 */
public class JsonNameResolverJacksonImpl implements JsonNameResolver {
    @Override
    public String jsonName(Field field) {
        final JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        return null!=jsonProperty
                ? jsonProperty.value()
                : field.getName();
    }

    @Override
    public Map<String, String> jsonNameDictionary(Class clazz) {
        final Field[] declaredFields = clazz.getDeclaredFields();

        return stream(declaredFields)
                .collect(toMap(Field::getName, this::jsonName));
    }
}
