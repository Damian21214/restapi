package pl.langer.edu.restapi.services;

import pl.langer.edu.restapi.domain.Employee;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.services.models.employee.EmployeeDetailsModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeShortModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeInModel;

import java.util.List;

/**
 * Created by DLanger on 2016-09-30.
 */
public interface EmployeeService {
    Long addNew(EmployeeInModel employeeInModel);
    void update(Long id, EmployeeInModel employeeInModel) throws NotFoundException;
    List<EmployeeShortModel> findAll();
    EmployeeDetailsModel findOne(Long id) throws NotFoundException;
    boolean assignOffice(Long employeeId, Long officeId) throws NotFoundException;
    Employee getOne(Long id) throws NotFoundException;
}
