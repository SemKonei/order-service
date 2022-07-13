package ru.semkonei.ordersvc.service;

import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ru.semkonei.ordersvc.testdata.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void save() {
        User newUser = getNew();
        User created = service.create(getNew());
        newUser.setId(created.id());
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(created.id()), newUser);
    }

    @Test
    public void get() {
        USER_MATCHER.assertMatch(service.get(USER_ID), user);
    }

    @Test
    public void getByEmail() {
        USER_MATCHER.assertMatch(service.getByEmail(user.getEmail()), user);
    }

    @Test
    public void getAll() {
        USER_MATCHER.assertMatch(service.getAll(), admin, guest, user);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("null@nu.ll"));
    }

    @Test
    public void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThat(service.delete(NOT_FOUND)).isEqualTo(false);
    }
}
