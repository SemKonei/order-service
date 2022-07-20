package ru.semkonei.ordersvc.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends NamedEntity {

    @NotBlank
    @Email
    @Size(max = 128)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 5, max = 128)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @NotNull
    @Column(name = "registered", nullable = false, updatable = false)
    private Date registered = new Date();

/*    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonManagedReference
    private List<Order> orders;*/

    public User() {
    }

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.enabled, u.registered);
    }

    public User(Integer id, String name) {
        super(id, name);
    }

    public User(Integer id, String name, String email, String password) {
        this(id, name, email, password, true, new Date());
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", email=").append(email);
        sb.append(", enabled=").append(enabled);
        sb.append(", registered=").append(registered)
                .append("}");
        return sb.toString();
    }
}
