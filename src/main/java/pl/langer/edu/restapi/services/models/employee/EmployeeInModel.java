package pl.langer.edu.restapi.services.models.employee;

import pl.langer.edu.restapi.services.models.shared.AddressModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by DLanger on 2016-09-30.
 */
public class EmployeeInModel {
    @Size(min = 5, max = 64)
    @NotNull
    private String name;

    private AddressModel address;

    @Size(max = 128)
    private String email;

    @Size(max = 32)
    private String phone;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
