package ru.semkonei.ordersvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.enabled, u.registered);
    }

    public User(Integer id, String name) {
        super(id, name);
    }

    public User(String name, String email, String password) {
        this(null, name, email, password, false, new Date());
    }
    public User(Integer id, String name, String email, String password) {
        this(id, name, email, password, false, new Date());
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        User that = (User) o;
        return id != null && id.equals(that.id)
                && enabled == that.enabled
                && Objects.equals(email, that.email)
                && Objects.equals(password, that.password)
                && Objects.equals(registered, that.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, registered);
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
