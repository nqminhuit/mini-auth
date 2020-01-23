package mini.auth.boot.entities.business;

import org.springframework.data.repository.CrudRepository;
import mini.auth.boot.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);

}
