package ru.semkonei.ordersvc.service;

import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ru.semkonei.ordersvc.testdata.OrderTestData.*;
import static ru.semkonei.ordersvc.testdata.UserTestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class OrderServiceTest extends AbstractServiceTest {

    @Autowired
    private OrderService service;

    @Test
    public void save() {
        Order created = service.create(getNew(), USER_ID);
        Order newOrder = getNew();
        newOrder.setId(created.id());
        ORDER_MATCHER.assertMatch(created, newOrder);
        ORDER_MATCHER.assertMatch(service.get(created.id(), USER_ID), newOrder);
    }

    @Test
    public void save2() {
        Order order1 = getNew();
        Order order2 = getNew();
        service.create(order1, USER_ID);
        service.create(order2, USER_ID);
        int i = 1;
    }

    @Test
    public void get() {
        ORDER_MATCHER.assertMatch(service.get(ORDER1_ID, USER_ID), order1);
    }

    @Test
    public void getAll() {
        ORDER_MATCHER.assertMatch(service.getAll(USER_ID), orderList);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void update() {
        Order updated = getUpdated();
        service.update(updated, USER_ID);
        ORDER_MATCHER.assertMatch(service.get(ORDER1_ID, USER_ID), getUpdated());
    }

    @Test
    public void delete() {
        service.delete(ORDER1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(ORDER1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThat(service.delete(NOT_FOUND, USER_ID)).isEqualTo(false);
    }
}

