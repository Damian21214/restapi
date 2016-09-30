package pl.langer.edu.restapi.services.implementation.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.langer.edu.restapi.domain.Office;
import pl.langer.edu.restapi.infrastructure.exceptions.NotFoundException;
import pl.langer.edu.restapi.repositories.OfficeRepository;
import pl.langer.edu.restapi.services.OfficeService;
import pl.langer.edu.restapi.services.models.office.OfficeInModel;
import pl.langer.edu.restapi.services.models.office.OfficeShortModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DLanger on 2016-09-30.
 */
@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository, ModelMapper modelMapper) {
        this.officeRepository = officeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<OfficeShortModel> findAll() {
        final List<Office> offices = this.officeRepository.findAll();
        ArrayList<OfficeShortModel> result = new ArrayList<>(offices.size());
        offices.forEach(office -> result.add(this.modelMapper.map(office, OfficeShortModel.class)));
        return result;
    }

    @Override
    public Long addNew(OfficeInModel officeInModel) {
        Office newOffice = this.modelMapper.map(officeInModel, Office.class);
        newOffice.setId(null);
        newOffice.setEmployees(null);
        Office saved = this.officeRepository.save(newOffice);
        return saved.getId();
    }

    @Override
    public Office getOne(Long id) throws NotFoundException {
        if(null==id) throw new IllegalArgumentException("Office id cannot be null");
        final Office office = this.officeRepository.findOne(id);
        if(null==office) throw new NotFoundException(String.format("Office with id %s not found", id));
        return office;
    }
}
