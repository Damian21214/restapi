package pl.langer.edu.restapi.domain;

import pl.langer.edu.restapi.domain.jpaconverters.UpperConverter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by DLanger on 2016-09-30.
 */
@Entity
public class Office extends AbstractEntity {
    @Column(nullable = false, length = 64, unique = true)
    @Convert(converter = UpperConverter.class)
    private String name;

    @Embedded
    private Address address;

    @ManyToMany(mappedBy = "offices", fetch = FetchType.LAZY)
    private Set<Employee> employees;

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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
