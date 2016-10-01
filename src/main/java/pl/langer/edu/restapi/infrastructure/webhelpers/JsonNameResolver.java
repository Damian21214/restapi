package pl.langer.edu.restapi.infrastructure.webhelpers;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Damian Langer on 01.10.16.
 */
public interface JsonNameResolver {
    String jsonName(Field field);
    Map<String,String> jsonNameDictionary(Class clazz);
}
