package mini.auth.repository;

import org.springframework.data.repository.CrudRepository;
import mini.auth.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);

}
