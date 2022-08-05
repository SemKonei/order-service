package ru.semkonei.ordersvc.util;

import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.util.toUtils.UserUtil;
import ru.semkonei.ordersvc.web.to.UserResponseTO;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private UserResponseTO userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserResponseTO newTo) {
        userTo = newTo;
    }

    public UserResponseTO getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}

