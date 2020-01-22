package mini.auth.boot;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.business.CustomerRepository;

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

    private void assertCustomer(Customer actualCustomer, String expectedUsername, String expectedRole) {
        assertThat(actualCustomer.getUsername()).isEqualTo(expectedUsername);
        assertThat(actualCustomer.getRole()).isEqualTo(expectedRole);
    }

    @Test
    public void shouldCreateCustomer() {
        // given:
        Customer customer = new Customer("clark", "superman");
        customer.setPassword("password");
        // when:
        controller.createNewCustomer(customer);

        // then:
        Customer createdCustomer = repository.findByUsername("clark").get(0);
        assertCustomer(createdCustomer, "clark", "superman");
    }

    private void saveCustomerToDb(String username, String role) {
        Customer customer = new Customer(username, role);
        customer.setPassword("password");
        repository.save(customer);
    }

    @Test
    public void shouldGetCorrectCustomer() {
        // given:
        saveCustomerToDb("batman", "the dark knight");

        // when:
        Customer createdCustomer = controller.findCustomerByUsername("batman");

        // then:
        assertCustomer(createdCustomer, "batman", "the dark knight");
    }

    @Test
    public void shouldUpdateCustomer() {
        // given:
        saveCustomerToDb("catwoman", "the thief");
        Customer existingCustomer = repository.findByUsername("catwoman").get(0);
        assertCustomer(existingCustomer, "catwoman", "the thief");

        // when:
        existingCustomer.setUsername("selina kyle");
        existingCustomer.setRole("waitress");
        controller.updateCustomer(existingCustomer.getId(), existingCustomer);

        // then:
        assertThat(repository.findByUsername("catwoman")).isEmpty();
        Customer updatedCustomer = repository.findByUsername("selina kyle").get(0);
        assertCustomer(updatedCustomer, "selina kyle", "waitress");
    }

}
