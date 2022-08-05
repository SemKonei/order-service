package ru.semkonei.ordersvc.web.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.semkonei.ordersvc.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseTO extends NamedTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String password;

    private boolean enabled;

    private Date registered;

    public UserResponseTO(User user) {
        super(user.getId(), user.getName());
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.registered = user.getRegistered();
    }

    public UserResponseTO(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
