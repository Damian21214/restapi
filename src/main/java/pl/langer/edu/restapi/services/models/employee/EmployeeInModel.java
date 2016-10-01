package pl.langer.edu.restapi.services.models.employee;

import pl.langer.edu.restapi.services.models.shared.AddressModel;

/**
 * Created by DLanger on 2016-09-30.
 */
public class EmployeeInModel {
    private String name;
    private AddressModel address;
    private String email;
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