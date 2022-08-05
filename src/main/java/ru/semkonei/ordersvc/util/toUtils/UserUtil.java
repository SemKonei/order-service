package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.web.to.UserRequestTO;
import ru.semkonei.ordersvc.web.to.UserResponseTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    public static List<UserResponseTO> getTos(List<User> users) {
        return users.stream().map(UserUtil::createTo).collect(Collectors.toList());
    }

    public static UserResponseTO createTo(User user) {
       return new UserResponseTO(user);
    }

    public static User createNewFromTo(UserRequestTO userTO) {
        return new User(null,
                userTO.getName(),
                userTO.getEmail().toLowerCase(),
                userTO.getPassword());
    }
    public static UserResponseTO asTo(User user) {
        return new UserResponseTO(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User updateFromTo(User user, UserRequestTO userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User getFromTo(UserRequestTO userTO) {
        User user = new User();
        user.setName(userTO.getName());
        user.setEmail(userTO.getEmail());
        user.setPassword(userTO.getPassword());
        return user;
    }
}
