package mini.auth.boot;

import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @GetMapping("/customer")
    public Customer getCustomer(@RequestParam(value = "name") String name) {
        return new Customer(0, name, new Date(), "customer");
    }

}
