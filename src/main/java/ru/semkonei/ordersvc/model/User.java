package ru.semkonei.ordersvc.model;

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
public class User extends NamedEntity {
    private String email;

    private String password;

    private boolean enabled = false;

    private Date registered = new Date();

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
