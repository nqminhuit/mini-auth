package mini.auth.boot;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.business.CustomerRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/details")
    public Customer findCustomerByUsername(@RequestParam(value = "name") String name) {
        return customerRepository.findByUsername(name).get(0);
    }

    @GetMapping("/all")
    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    @PostMapping(path = "/create",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Customer createNewCustomer(@RequestBody Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @PutMapping(path = "/{id}",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Customer updateCustomer(
        @PathVariable("id") Long id, @RequestBody Customer newCustomer) {

        Customer existing = customerRepository.findById(id).get();
        existing.setRole(newCustomer.getRole());
        existing.setUsername(newCustomer.getUsername());
        return customerRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void deleteCustomer(@PathVariable("id") Long customerId) {
        customerRepository.deleteById(customerId);
    }

}
