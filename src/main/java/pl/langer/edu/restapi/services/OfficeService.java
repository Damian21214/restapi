package pl.langer.edu.restapi.services;

import pl.langer.edu.restapi.domain.Office;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.services.models.office.OfficeInModel;
import pl.langer.edu.restapi.services.models.office.OfficeShortModel;

import java.util.List;

/**
 * Created by DLanger on 2016-09-30.
 */
public interface OfficeService {
    List<OfficeShortModel> findAll();

    Long addNew(OfficeInModel officeInModel);

    Office getOne(Long id) throws NotFoundException;
}
