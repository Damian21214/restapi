package pl.langer.edu.restapi.services.implementation.jpa;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import pl.langer.edu.restapi.StartRestApi;
import pl.langer.edu.restapi.domain.Employee;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.services.EmployeeService;
import pl.langer.edu.restapi.services.models.employee.EmployeeDetailsModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeInModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeShortModel;
import pl.langer.edu.restapi.services.models.shared.AddressModel;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Damian Langer on 01.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StartRestApi.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:expectedAfterAdd.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void addNew() throws Exception {
        EmployeeInModel newEmployee = new EmployeeInModel();
        newEmployee.setName("new employee");
        newEmployee.setEmail("new@email.pl");
        newEmployee.setAddress(new AddressModel());
        newEmployee.getAddress().setStreet("new street");
        final Long added = this.employeeService.addNew(newEmployee);

        assertThat("Should return id 5", added, is(5L));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:expectedAfterUpdate.xml", assertionMode = DatabaseAssertionMode
            .NON_STRICT_UNORDERED)
    public void update() throws Exception {
        EmployeeInModel newEmployee = new EmployeeInModel();
        newEmployee.setName("new employee");
        newEmployee.setEmail("new@email.pl");
        newEmployee.setAddress(new AddressModel());
        newEmployee.getAddress().setStreet("new street");
        this.employeeService.update(1L, newEmployee);
    }

    @Test(expected = NotFoundException.class)
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:initDb.xml", assertionMode = DatabaseAssertionMode
            .NON_STRICT_UNORDERED)
    public void updateNonExistedEmployee() throws Exception {
        EmployeeInModel newEmployee = new EmployeeInModel();
        newEmployee.setName("new employee");
        newEmployee.setEmail("new@email.pl");
        newEmployee.setAddress(new AddressModel());
        newEmployee.getAddress().setStreet("new street");
        this.employeeService.update(10L, newEmployee);
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void findAll() throws Exception {
        final List<EmployeeShortModel> employees = this.employeeService.findAll();

        assertThat("Should contains 4 employees",
                employees, hasItems(
                allOf(
                       hasProperty("id", is(1L)),
                       hasProperty("name", is("DAMIAN1")),
                       hasProperty("email", is("damian1@email.pl"))
                        ),
                allOf(
                    hasProperty("id", is(2L)),
                    hasProperty("name", is("DAMIAN2")),
                    hasProperty("email", is("damian2@email.pl"))
                ),
                allOf(
                    hasProperty("id", is(3L)),
                    hasProperty("name", is("DAMIAN3")),
                    hasProperty("email", is("damian3@email.pl"))
            ),
                allOf(
                    hasProperty("id", is(4L)),
                    hasProperty("name", is("DAMIAN4")),
                    hasProperty("email", is("damian4@email.pl"))
                )
        ));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void findOne() throws Exception {
        final EmployeeDetailsModel employee = this.employeeService.findOne(2L);

        assertThat("Should return details of employee #2", employee,
                allOf(
                    hasProperty("id", is(2L)),
                    hasProperty("name", is("DAMIAN2")),
                    hasProperty("email", is("damian2@email.pl")),
                    hasProperty("address", is(hasProperty("street", is("street2")))),
                    hasProperty("offices", hasSize(2))
        ));
    }

    @Test(expected = NotFoundException.class)
    @DatabaseSetup("classpath:initDb.xml")
    public void findOneNotExistedEmployee() throws Exception {
        final EmployeeDetailsModel employee = this.employeeService.findOne(10L);
    }


    @Test
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:expectedAfterAssignment.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void assignOffice() throws Exception {
        final boolean result = this.employeeService.assignOffice(4L, 1L);
        assertThat(result, is(true));
    }

    @Test(expected = NotFoundException.class)
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:initDb.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void assignNonExistedOffice() throws Exception {
        this.employeeService.assignOffice(4L,10L);
    }

    @Test(expected = NotFoundException.class)
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:initDb.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void assignNonExistedEmployee() throws Exception {
        this.employeeService.assignOffice(10L,1L);
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    @ExpectedDatabase(value = "classpath:initDb.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void assignCurrentlyAssignedOffice() throws Exception {
        final boolean result = this.employeeService.assignOffice(1L, 1L);
        assertThat(result, is(false));
    }

    @Test
    @DatabaseSetup("classpath:initDb.xml")
    public void getOne() throws Exception {
        final Employee employee = this.employeeService.getOne(2L);

        assertThat("Should return details of employee #2", employee,
                allOf(
                        hasProperty("id", is(2L)),
                        hasProperty("name", is("DAMIAN2")),
                        hasProperty("email", is("damian2@email.pl")),
                        hasProperty("address", is(hasProperty("street", is("street2"))))
                ));
    }

    @Test(expected = NotFoundException.class)
    @DatabaseSetup("classpath:initDb.xml")
    public void getOneNonExistedEmployee() throws Exception {
        final Employee employee = this.employeeService.getOne(10L);
    }

}
