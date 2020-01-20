package mini.auth.boot;

import java.util.Date;

public class Customer {

    private long id;

    private String username;

    private Date dateOfBirth;

    private String role;

    public Customer(long id, String username, Date dateOfBirth, String role) {
        this.id = id;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
