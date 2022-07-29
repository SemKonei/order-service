package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import lombok.Setter;
import ru.semkonei.ordersvc.model.User;

@Getter
@Setter
public class UserRequestTOTest extends UserRequestTO {

    public UserRequestTOTest(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
