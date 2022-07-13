package ru.semkonei.ordersvc.service;

import org.springframework.dao.DataIntegrityViolationException;
import ru.semkonei.ordersvc.model.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.assertThrows;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.ORDERMERCH1_ID;
import static ru.semkonei.ordersvc.testdata.OrderTestData.ORDER_MATCHER;
import static ru.semkonei.ordersvc.testdata.OrderTestData.getNew;
import static ru.semkonei.ordersvc.testdata.UserTestData.USER_ID;

public class OrderDataServiceTest  extends AbstractServiceTest {

    @Autowired
    private OrderDataService orderDataService;
    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        Order newOrder = getNew();
        Order created = orderDataService.create(Map.of(merch1, 1),USER_ID);
        newOrder.setId(created.getId());
        ORDER_MATCHER.assertMatch(created, newOrder);
        ORDER_MATCHER.assertMatch(orderService.get(ORDERMERCH1_ID+2, USER_ID), newOrder);
    }

    @Test
    public void createWithNegativeCount() {
        assertThrows(DataIntegrityViolationException.class, () -> orderDataService.create(Map.of(merch1, -1),USER_ID));
    }
}