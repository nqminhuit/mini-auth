package mini.auth.boot;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.business.CustomerRepository;

@SpringBootTest
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

        // when:
        controller.createNewCustomer(customer);

        // then:
        Customer createdCustomer = repository.findByUsername("clark").get(0);
        assertCustomer(createdCustomer, "clark", "superman");
    }

    @Test
    public void shouldGetCorrectCustomer() {
        // given:
        repository.save(new Customer("batman", "the dark knight"));

        // when:
        Customer createdCustomer = controller.findCustomerByUsername("batman");

        // then:
        assertCustomer(createdCustomer, "batman", "the dark knight");
    }

    @Test
    public void shouldUpdateCustomer() {
        // given:
        repository.save(new Customer("catwoman", "the thief"));
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