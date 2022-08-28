package ru.semkonei.ordersvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;

public class MerchServiceTest extends AbstractServiceTest {

    @Autowired
    private MerchService service;

    @Test
    void save() {
        Merch newUser = getNew();
        Merch created = service.create(getNew());
        newUser.setId(created.id());
        MERCH_MATCHER.assertMatch(created, newUser);
        MERCH_MATCHER.assertMatch(service.get(created.id()), newUser);
    }

    @Test
    void get() {
        MERCH_MATCHER.assertMatch(service.get(MERCH1_ID), merch1);
    }

    @Test
    void getAll() {
        MERCH_MATCHER.assertMatch(service.getAll(), merch1, merch2);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void update() {
        Merch updated = getUpdated();
        service.update(updated);
        MERCH_MATCHER.assertMatch(service.get(MERCH1_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(MERCH1_ID);
        assertThrows(NotFoundException.class, () -> service.get(MERCH1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }
}

