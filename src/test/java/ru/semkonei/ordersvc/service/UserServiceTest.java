package ru.semkonei.ordersvc.service;

import org.junit.jupiter.api.Test;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.semkonei.ordersvc.testdata.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void save() {
        User newUser = getNew();
        User created = service.create(getNew());
        newUser.setId(created.id());
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(created.id()), newUser);
    }

    @Test
    void get() {
        USER_MATCHER.assertMatch(service.get(USER_ID), user);
    }

    @Test
    void getByEmail() {
        USER_MATCHER.assertMatch(service.getByEmail(user.getEmail()), user);
    }

    @Test
    void getAll() {
        USER_MATCHER.assertMatch(service.getAll(), user, admin, guest);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("null@nu.ll"));
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
}
