package ru.semkonei.ordersvc.testdata;

import ru.semkonei.ordersvc.MatcherFactory;
import ru.semkonei.ordersvc.model.Order;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.semkonei.ordersvc.model.BaseEntity.START_SEQ;
import static ru.semkonei.ordersvc.testdata.UserTestData.guest;
import static ru.semkonei.ordersvc.testdata.UserTestData.user;

public class OrderTestData {
    public static final MatcherFactory.Matcher<Order> ORDER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("user", "orderDate");

    public static final int ORDER1_ID = START_SEQ + 5;
    public static final int NOT_FOUND = 10;

    public static final Order order1 = new Order(ORDER1_ID, LocalDateTime.of(2022, 3, 1, 1, 11), user);
    public static final Order order2 = new Order(ORDER1_ID+1, LocalDateTime.of(2022, 4, 2, 3, 33), user);
    public static final Order order3 = new Order(ORDER1_ID+2, LocalDateTime.of(2022, 5, 3, 4, 44), guest);

    public static List<Order> orderList = Arrays.asList(order1, order2, order3);

    public static Order getNew() {
        return new Order(null, LocalDateTime.of(2022, 2, 3, 2, 22), user);
    }
    public static Order getUpdated() {
        Order updated = new Order(order1);
        return updated;
    }
}
