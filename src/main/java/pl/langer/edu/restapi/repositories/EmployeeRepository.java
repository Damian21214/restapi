package pl.langer.edu.restapi.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.langer.edu.restapi.domain.Employee;

import java.util.List;

/**
 * Created by DLanger on 2016-09-30.
 */
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    @Override
    List<Employee> findAll();
}
