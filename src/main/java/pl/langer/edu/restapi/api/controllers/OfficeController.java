package pl.langer.edu.restapi.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import pl.langer.edu.restapi.api.messages.BaseApiResponse;
import pl.langer.edu.restapi.services.OfficeService;
import pl.langer.edu.restapi.services.models.office.OfficeInModel;
import pl.langer.edu.restapi.services.models.office.OfficeShortModel;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Damian Langer on 30.09.16.
 */
@RestController
@RequestMapping(value = "/api")
public class OfficeController {
    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @RequestMapping(value = "/office",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> addNew(@RequestBody @Valid final OfficeInModel officeInModel,
                                              WebRequest request) {
        final Long officeId = this.officeService.addNew(officeInModel);
        BaseApiResponse response = new BaseApiResponse("New office created", request, officeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/offices",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<BaseApiResponse> findAll(WebRequest request) {
        final List<OfficeShortModel> offices = this.officeService.findAll();
        BaseApiResponse response = new BaseApiResponse("All offices", request, offices);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
