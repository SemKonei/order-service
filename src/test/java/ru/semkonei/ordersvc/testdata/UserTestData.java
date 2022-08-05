package ru.semkonei.ordersvc.testdata;

import ru.semkonei.ordersvc.MatcherFactory;
import ru.semkonei.ordersvc.model.Role;
import ru.semkonei.ordersvc.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.semkonei.ordersvc.model.BaseEntity.START_SEQ;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class,"registered", "orders", "roles");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password",  Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");

    public static List<User> userList = Arrays.asList(user, admin, guest);

    public static User getNew() {
        return new User(null, "test", "test@mail.ya","test");
    }
    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("updated");
        updated.setEmail("updated@test.ru");
        updated.setPassword("updPass");
        updated.setEnabled(true);
        return updated;
    }
}
