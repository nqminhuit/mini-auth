package mini.auth.boot;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
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
import mini.auth.entity.Customer;
import mini.auth.repository.CustomerRepository;
import mini.auth.security.bean.JwtServiceBean;
import mini.auth.security.service.MyUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerRestTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository repository;

    @MockBean
    private JwtServiceBean jwtServiceBean;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    private static final String ADMIN_USER = "ADMIN_USER";

    private static final String ADMIN_ROLE = "admin";

    private static final String PUBLIC_USER = "PUBLIC_USER";

    private static final String USER_ROLE = "user";

    private void prepareCustomer() {
        Customer admin = createCustomer(0L, ADMIN_USER, ADMIN_ROLE, "password1");
        given(repository.findByUsername(ADMIN_USER)).willReturn(admin);
        given(repository.findById(0L)).willReturn(Optional.of(admin));

        given(repository.findByUsername(PUBLIC_USER))
            .willReturn(createCustomer(1L, PUBLIC_USER, USER_ROLE, "password2"));
    }

    private Customer createCustomer(Long id, String username, String role, String password) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setRole(role);
        return customer;
    }

    @Test
    @WithMockUser(username = ADMIN_USER, authorities = { ADMIN_ROLE })
    public void adminCanCreateCustomer() throws Exception {
        Customer newCustomer = createCustomer(-1L, "batman", ADMIN_ROLE, "batpass");
        mvc.perform(post("/api/customer/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().isOk());
    }

    @Test
    public void anonymousUserCanNotCreateCustomer() throws Exception {
        Customer newCustomer = createCustomer(-1L, "batman", ADMIN_ROLE, "bruce wayne");
        mvc.perform(post("/api/customer/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser(username = PUBLIC_USER, authorities = { USER_ROLE })
    public void publicUserCanNotCreateCustomer() throws Exception {
        Customer newCustomer = createCustomer(-1L, "lucius", ADMIN_ROLE, "fox");
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
                    "\",\"role\":\"user\",\"password\":\"password2\"}"));
    }

    @Test
    @WithMockUser(username = ADMIN_USER, authorities = { ADMIN_ROLE })
    public void adminCanUpdateCustomer() throws Exception {
        // given:
        Customer newCustomer = createCustomer(0L, "joker", ADMIN_ROLE, "johndoe");
        prepareCustomer();

        // when:
        mvc.perform(put("/api/customer/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = PUBLIC_USER, authorities = { USER_ROLE })
    public void publicUserCanNotUpdateCustomer() throws Exception {
        // given:
        Customer newCustomer = createCustomer(0L, "joker", ADMIN_ROLE, "johndoe");

        // when:
        mvc.perform(put("/api/customer/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCustomer.toJson()))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = ADMIN_USER, authorities = { ADMIN_ROLE })
    public void adminCanDeleteCustomer() throws Exception {
        // given:
        prepareCustomer();

        // when:
        mvc.perform(delete("/api/customer/0")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = PUBLIC_USER, authorities = { USER_ROLE })
    public void publicUserCanNotDeleteCustomer() throws Exception {
        mvc.perform(delete("/api/customer/0")).andExpect(status().isForbidden());
    }

}
