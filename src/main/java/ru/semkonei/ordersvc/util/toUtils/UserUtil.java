package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.to.MerchTO;
import ru.semkonei.ordersvc.to.UserTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    public static List<UserTO> getTos(List<User> users) {
        return users.stream().map(UserUtil::createTo).collect(Collectors.toList());
    }

    public static UserTO createTo(User user) {
        return new UserTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getRegistered());
    }

    public static User getFromTo(UserTO userTO) {
        return new User(userTO.getId(), userTO.getName(), userTO.getEmail(),
                userTO.getPassword(), userTO.isEnabled(), userTO.getRegistered());
    }
}
