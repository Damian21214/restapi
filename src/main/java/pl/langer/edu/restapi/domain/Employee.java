package pl.langer.edu.restapi.domain;

import pl.langer.edu.restapi.domain.jpaconverters.UpperConverter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by DLanger on 2016-09-30.
 */
@Entity
public class Employee extends AbstractEntity {
    @Column(nullable = false, unique = true, length = 64)
    @Convert(converter = UpperConverter.class)
    private String name;

    @Embedded
    private Address address;

    @Column(length = 128)
    private String email;

    @Column(length = 32)
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {
            @JoinColumn(name = "employee_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "office_id")
    })
    private Set<Office> offices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }
}
