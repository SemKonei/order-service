package ru.semkonei.ordersvc.service;

import org.junit.jupiter.api.Test;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderTestData.ORDER1_ID;
import static ru.semkonei.ordersvc.testdata.UserTestData.USER_ID;

public class OrderMerchServiceTest extends AbstractServiceTest {

    @Autowired
    private OrderMerchService service;

    @Test
    void get() {
        ORDERMERCH_MATCHER.assertMatch(service.get(ORDERMERCH1_ID, ORDER1_ID, USER_ID), ORDER_DATA_1);
    }

    @Test
    void getAll() {
        ORDERMERCH_MATCHER.assertMatch(service.getAll(ORDER1_ID, USER_ID), orderMerchList);
    }

    @Test
    void getNotFoundMerch() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ORDER1_ID, USER_ID));
    }


    @Test
    void update() {
        OrderMerch updated = getUpdated();
        service.update(updated, USER_ID);
        ORDERMERCH_MATCHER.assertMatch(service.get(ORDERMERCH1_ID, ORDER1_ID, USER_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(ORDERMERCH1_ID, ORDER1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(ORDERMERCH1_ID, ORDER1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ORDER1_ID, USER_ID));
    }
}
