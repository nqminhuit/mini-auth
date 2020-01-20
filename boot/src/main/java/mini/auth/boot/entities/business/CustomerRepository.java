package mini.auth.boot.entities.business;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import mini.auth.boot.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByUsername(String username);

}
