package pl.langer.edu.restapi.infrastructure.webhelpers.implementation;

import org.junit.Test;
import pl.langer.edu.restapi.infrastructure.webhelpers.JsonNameResolver;

import java.lang.reflect.Field;
import java.util.Map;


import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Damian Langer on 01.10.16.
 */
public class JsonNameResolverJacksonImplTest {
    @Test
    public void jsonNameShouldReturnJsonName() throws Exception {
        GoodClazzForTest goodClazzForTest = new GoodClazzForTest();
        final Field field = goodClazzForTest.getClass().getDeclaredField("annotatedNotDefault");
        JsonNameResolver jsonNameResolver = new JsonNameResolverJacksonImpl();
        final String jsonName = jsonNameResolver.jsonName(field);

        assertThat(jsonName, is("jsonNameA"));
    }

    @Test
    public void jsonNameShouldReturnFieldName() throws Exception {
        GoodClazzForTest goodClazzForTest = new GoodClazzForTest();
        final Field field = goodClazzForTest.getClass().getDeclaredField("notAnnotated");
        JsonNameResolver jsonNameResolver = new JsonNameResolverJacksonImpl();
        final String jsonName = jsonNameResolver.jsonName(field);

        assertThat(jsonName, is("notAnnotated"));
    }

    @Test
    public void jsonNameMapShouldReturnAllFieldsMap() throws Exception {
        JsonNameResolver jsonNameResolver = new JsonNameResolverJacksonImpl();
        final Map<String, String> jsonNameDictionary = jsonNameResolver.jsonNameDictionary(GoodClazzForTest.class);

        assertThat(jsonNameDictionary.entrySet(), containsInAnyOrder(
                is(allOf(hasProperty("key", is("annotatedNotDefault")), hasProperty("value", is("jsonNameA")))),
                is(allOf(hasProperty("key", is("getAnnotatedDefault")), hasProperty("value", is("jsonNameB")))),
                is(allOf(hasProperty("key", is("notAnnotated")), hasProperty("value", is("notAnnotated"))))
        ));
    }

}
