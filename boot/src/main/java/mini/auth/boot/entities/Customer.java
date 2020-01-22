package mini.auth.boot.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    protected Customer() {
    }

    public Customer(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public Customer(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomerEntity [id=" + id + ", role=" + role + ", username=" + username + "]";
    }

    public String toJson() {
        return "{\"role\":\"" + role + "\", \"username\":\"" + username + "\"}";
    }

}
