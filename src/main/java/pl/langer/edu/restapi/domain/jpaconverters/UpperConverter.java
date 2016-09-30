package pl.langer.edu.restapi.domain.jpaconverters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by DLanger on 2016-05-10.
 */
@Converter
public class UpperConverter implements AttributeConverter<String, String>{
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return null!=attribute?attribute.toUpperCase():null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return null!=dbData?dbData.toUpperCase():null;
    }
}
