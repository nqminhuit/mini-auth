package mini.auth.boot;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import mini.auth.boot.controller.CustomerController;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.repository.CustomerRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerControllerCrudTest {

    @Autowired
    private CustomerController controller;

    @Autowired
    private CustomerRepository repository;

    @Test
    public void customerControllerInstanceCreated() throws Exception {
        assertThat(controller).isNotNull();
    }

    private void assertCustomer(Customer actualCustomer,
        String expectedUsername, String expectedRole, String expectedPass) {

        assertThat(actualCustomer.getUsername()).isEqualTo(expectedUsername);
        assertThat(actualCustomer.getRole()).isEqualTo(expectedRole);
        assertThat(actualCustomer.getPassword()).isEqualTo(expectedPass);
    }

    @Test
    public void shouldCreateCustomer() {
        // given:
        Customer customer = createCustomer("clark", "superman", "superpassword");
        // when:
        controller.createNewCustomer(customer);

        // then:
        Customer createdCustomer = repository.findByUsername("clark");
        assertCustomer(createdCustomer, "clark", "superman", "superpassword");
    }

    private Customer createCustomer(String username, String role, String password) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setRole(role);
        return customer;
    }

    private void saveCustomerToDb(String username, String role, String password) {
        Customer customer = createCustomer(username, role, password);
        repository.save(customer);
    }

    @Test
    public void shouldGetCorrectCustomer() {
        // given:
        saveCustomerToDb("batman", "the dark knight", "batpass");

        // when:
        Customer createdCustomer = controller.findCustomerByUsername("batman");

        // then:
        assertCustomer(createdCustomer, "batman", "the dark knight", "batpass");
    }

    @Test
    public void shouldUpdateCustomer() {
        // given:
        saveCustomerToDb("catwoman", "the thief", "nin9liv9s");
        Customer existingCustomer = repository.findByUsername("catwoman");
        assertCustomer(existingCustomer, "catwoman", "the thief", "nin9liv9s");

        // when:
        existingCustomer.setUsername("selina kyle");
        existingCustomer.setRole("waitress");
        controller.updateCustomer(existingCustomer.getId(), existingCustomer);

        // then:
        assertThat(repository.findByUsername("catwoman")).isNull();
        Customer updatedCustomer = repository.findByUsername("selina kyle");
        assertCustomer(updatedCustomer, "selina kyle", "waitress", "nin9liv9s");
    }

}
