package mini.auth.boot;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.business.CustomerRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository repository;

    @Test
    public void createCustomer() throws Exception {
        Customer newCustomer = new Customer("batman", "the dark knight");
        mvc.perform(post("/api/customer/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().isOk());
    }

    private void prepareCustomer() {
        given(repository.findByUsername("batman"))
            .willReturn(Collections.singletonList(new Customer(0L, "batman", "the dark knight")));
        given(repository.findByUsername("clark"))
            .willReturn(Collections.singletonList(new Customer(1L, "clark", "superman")));
    }

    @Test
    public void getCustomer() throws Exception {
        // given:
        prepareCustomer();

        // when:
        mvc.perform(get("/api/customer/details?name=clark"))
            .andExpect(content().string("{\"id\":1,\"username\":\"clark\",\"role\":\"superman\"}"));
    }

    // @Test TODO minh: implement!
    public void updateCustomer() {
        // given:

        // when:

        // then:
    }

    // @Test TODO minh: implement!
    public void deleteCustomer() {
        // given:

        // when:

        // then:
    }

}
