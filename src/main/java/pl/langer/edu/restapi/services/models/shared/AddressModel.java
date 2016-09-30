package pl.langer.edu.restapi.services.models.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

/**
 * Created by DLanger on 2016-09-30.
 */
public class AddressModel {
    @JsonProperty("street")
    @Size(max = 64)
    private String street;

    @JsonProperty("street_no")
    @Size(max = 8)
    private String streetNo;

    @JsonProperty("city")
    @Size(max = 32)
    private String city;

    @JsonProperty("country")
    @Size(max = 64)
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
