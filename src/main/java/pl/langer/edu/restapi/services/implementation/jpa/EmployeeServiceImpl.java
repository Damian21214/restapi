package pl.langer.edu.restapi.services.implementation.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.langer.edu.restapi.domain.Address;
import pl.langer.edu.restapi.domain.Employee;
import pl.langer.edu.restapi.domain.Office;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.repositories.EmployeeRepository;
import pl.langer.edu.restapi.services.EmployeeService;
import pl.langer.edu.restapi.services.OfficeService;
import pl.langer.edu.restapi.services.models.employee.EmployeeDetailsModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeShortModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeInModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DLanger on 2016-09-30.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    private final OfficeService officeService;

    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper,
                               EmployeeRepository employeeRepository, OfficeService officeService) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.officeService = officeService;
    }


    @Override
    public Long addNew(EmployeeInModel employeeInModel) {
        Employee newEmployee = this.modelMapper.map(employeeInModel, Employee.class);
        newEmployee.setId(null);
        newEmployee.setOffices(null);
        Employee saved = this.employeeRepository.save(newEmployee);
        return saved.getId();
    }

    @Override
    public void update(Long id, EmployeeInModel employeeInModel) throws NotFoundException {
        if(null==id) throw new IllegalArgumentException("Id can not be null");
        if(null== employeeInModel) throw new IllegalArgumentException("Employee model can not be null");
        Employee forUpdate = this.employeeRepository.findOne(id);
        if(null==forUpdate) throw new NotFoundException("Employee not found");
        forUpdate = updateFromModel(forUpdate, employeeInModel);
        this.employeeRepository.save(forUpdate);
    }

    @Override
    public List<EmployeeShortModel> findAll() {
        final List<Employee> employees = this.employeeRepository.findAll();
        ArrayList<EmployeeShortModel> result = new ArrayList<>(employees.size());
        employees.forEach(employee -> result.add(this.modelMapper.map(employee, EmployeeShortModel.class)));
        return result;
    }

    @Transactional
    @Override
    public EmployeeDetailsModel findOne(Long id) throws NotFoundException {
        Employee employee = this.getOne(id);
        return modelMapper.map(employee, EmployeeDetailsModel.class);
    }

    @Transactional
    @Override
    public boolean assignOffice(Long employeeId, Long officeId) throws NotFoundException {
        Employee employee = this.getOne(employeeId);
        Office office = this.officeService.getOne(officeId);
        boolean result = employee.getOffices().add(office);
        if(result) {
            this.employeeRepository.save(employee);
        }
        return result;
    }

    @Override
    public Employee getOne(Long id) throws NotFoundException {
        if(null==id) throw new IllegalArgumentException("Employee id can not be null");
        final Employee employee = this.employeeRepository.findOne(id);
        if(null==employee) throw new NotFoundException(String.format("Employee with id %s not found", id));
        return employee;
    }

    private Employee updateFromModel(Employee forUpdate, EmployeeInModel employeeInModel) {
        forUpdate.setAddress(this.modelMapper.map(employeeInModel.getAddress(), Address.class));
        forUpdate.setEmail(employeeInModel.getEmail());
        forUpdate.setPhone(employeeInModel.getPhone());
        return forUpdate;
    }
}
