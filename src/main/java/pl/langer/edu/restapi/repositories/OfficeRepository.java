package pl.langer.edu.restapi.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.langer.edu.restapi.domain.Office;

import java.util.List;

/**
 * Created by DLanger on 2016-09-30.
 */
@Repository
public interface OfficeRepository extends PagingAndSortingRepository<Office,Long> {
    @Override
    List<Office> findAll();
}
