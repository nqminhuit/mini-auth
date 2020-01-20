package mini.auth.boot;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @Test
    public void customerControllerInstanceCreated() throws Exception {
        assertThat(customerController).isNotNull();
    }

    @Test
    public void shouldReturnCorrectCustomer() {
        Customer batman = customerController.getCustomer("batman");
        assertThat(batman.getId()).isEqualTo(0L);
        assertThat(batman.getUsername()).isEqualTo("batman");
        assertThat(batman.getDateOfBirth()).isEqualToIgnoringSeconds(new Date());
        assertThat(batman.getRole()).isEqualTo("customer");
    }

}
