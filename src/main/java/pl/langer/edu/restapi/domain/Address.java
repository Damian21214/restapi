package pl.langer.edu.restapi.domain;

import pl.langer.edu.restapi.domain.jpaconverters.UpperConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

/**
 * Created by DLanger on 2016-09-30.
 */
@Embeddable
public class Address {
    @Column(length = 64)
    private String street;

    @Column(length = 8)
    private String streetNo;

    @Column(length = 32)
    @Convert(converter = UpperConverter.class)
    private String city;

    @Column(length = 64)
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
