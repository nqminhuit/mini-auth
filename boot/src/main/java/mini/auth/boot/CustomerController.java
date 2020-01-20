package mini.auth.boot;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.business.CustomerRepository;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public Customer getCustomer(@RequestParam(value = "name") String name) {
        return new Customer(name, "customer");
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    @PostMapping(path = "/customer/create", consumes = "application/json", produces = "application/json")
    public @ResponseBody Customer createNewCustomer(@RequestBody Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @PutMapping("/customer/{id}")
    public @ResponseBody Customer updateCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @DeleteMapping("/customer/{id}")
    public @ResponseBody void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

}
