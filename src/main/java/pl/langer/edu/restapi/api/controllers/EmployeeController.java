package pl.langer.edu.restapi.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.langer.edu.restapi.api.messages.BaseApiResponse;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.services.EmployeeService;
import pl.langer.edu.restapi.services.models.employee.EmployeeDetailsModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeShortModel;
import pl.langer.edu.restapi.services.models.employee.EmployeeInModel;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Damian Langer on 30.09.16.
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/employee/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> findOne(@PathVariable("id") final Long employeeId,
                                               WebRequest request) throws NotFoundException {
        final EmployeeDetailsModel employee = this.employeeService.findOne(employeeId);
        final BaseApiResponse response  = new BaseApiResponse(String.format("Employee with id %s details", employeeId),
                request,
                employee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/employee",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> addNewEmployee(@RequestBody @Valid final EmployeeInModel employeeInModel,
                                                      WebRequest request) {
        final Long employeeId = this.employeeService.addNew(employeeInModel);
        BaseApiResponse response = new BaseApiResponse("Employee created", request, employeeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employees",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> findAllEmployees(WebRequest request) {
        final List<EmployeeShortModel> employees = this.employeeService.findAll();
        BaseApiResponse response = new BaseApiResponse("All employees", request, employees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{id}",
    method = RequestMethod.PUT,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> updateEmployee(@PathVariable("id") Long employeeId,
                                                      @RequestBody @Valid EmployeeInModel employeeInModel,
                                                      WebRequest request) throws NotFoundException {
        this.employeeService.update(employeeId, employeeInModel);
        BaseApiResponse response  = new BaseApiResponse(String.format("Employee with id %s updated", employeeId),
                request,
                null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/employee/{id}/workplace/{workplaceid}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> assignOffice(@PathVariable("id") Long employeeId,
                                                      @PathVariable("workplaceid") Long workplaceId,
                                                      WebRequest request) throws NotFoundException {
        final boolean assigned = this.employeeService.assignOffice(employeeId, workplaceId);
        BaseApiResponse response  = new BaseApiResponse(String.format("Employee with id %s %supdated", employeeId,
                assigned?"":"not "),
                request,
                assigned);
        return new ResponseEntity<>(response, assigned?HttpStatus.ACCEPTED:HttpStatus.NOT_MODIFIED);
    }
}
