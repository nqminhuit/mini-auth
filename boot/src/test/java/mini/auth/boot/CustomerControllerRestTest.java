package mini.auth.boot;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import mini.auth.boot.controller.CustomerController;
import mini.auth.boot.entities.Customer;
import mini.auth.boot.entities.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository repository;

    private static final String ADMIN_USER = "ADMIN_USER";

    private static final String ADMIN_ROLE = "admin";

    private static final String PUBLIC_USER = "PUBLIC_USER";

    private static final String USER_ROLE = "user";

    private void prepareCustomer() {
        given(repository.findByUsername(ADMIN_USER))
            .willReturn(new Customer(0L, ADMIN_USER, ADMIN_ROLE));
        given(repository.findByUsername(PUBLIC_USER))
            .willReturn(new Customer(1L, PUBLIC_USER, USER_ROLE));
    }

    @Test
    @WithMockUser(username = ADMIN_USER, authorities = { ADMIN_ROLE })
    public void adminCanCreateCustomer() throws Exception {
        Customer newCustomer = new Customer("batman", "the dark knight");
        mvc.perform(post("/api/customer/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().isOk());
    }

    @Test
    public void anonymousUserCanNotCreateCustomer() throws Exception {
        Customer newCustomer = new Customer("batman", "the dark knight");
        mvc.perform(post("/api/customer/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @WithMockUser(username = PUBLIC_USER, authorities = { USER_ROLE })
    public void publicUserCanNotCreateCustomer() throws Exception {
        Customer newCustomer = new Customer("batman", "the dark knight");
        mvc.perform(post("/api/customer/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().isForbidden());
    }

    @Test
    public void anonymousUserCanGetCustomerDetails() throws Exception {
        // given:
        prepareCustomer();

        // when:
        mvc.perform(get("/api/customer/details?name=" + PUBLIC_USER))
            .andExpect(
                content().string("{\"id\":1,\"username\":\"" + PUBLIC_USER +
                    "\",\"role\":\"user\",\"password\":null}"));
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
