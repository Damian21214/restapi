package pl.langer.edu.restapi.infrastructure.webhelpers.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DLanger on 2016-08-23.
 */
public class GoodClazzForTest {
    @JsonProperty("jsonNameA")
    private String annotatedNotDefault;

    @JsonProperty("jsonNameB")
    private String getAnnotatedDefault;

    private String notAnnotated;
}
