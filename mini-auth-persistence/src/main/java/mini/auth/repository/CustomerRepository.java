package mini.auth.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mini.auth.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);

    List<Customer> findAllByOrderByIdAsc(); // 2 "By"s is requried

}
