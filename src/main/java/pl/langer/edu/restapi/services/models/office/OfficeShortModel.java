package pl.langer.edu.restapi.services.models.office;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.langer.edu.restapi.services.models.shared.AddressModel;

import javax.validation.constraints.Size;

/**
 * Created by DLanger on 2016-09-30.
 */
public class OfficeShortModel {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @Size(max = 64)
    private String name;

    @JsonProperty("address")
    private AddressModel address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }
}
