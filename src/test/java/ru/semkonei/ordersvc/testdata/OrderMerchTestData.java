package ru.semkonei.ordersvc.testdata;

import ru.semkonei.ordersvc.MatcherFactory;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;

import java.util.Arrays;
import java.util.List;

import static ru.semkonei.ordersvc.model.BaseEntity.START_SEQ;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderTestData.order1;
import static ru.semkonei.ordersvc.testdata.OrderTestData.order2;

public class OrderMerchTestData {
    public static final MatcherFactory.Matcher<OrderMerch> ORDERMERCH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderMerch.class, "merch", "order");
    public static final MatcherFactory.Matcher<OrderMerchResponseTO> ORDERMERCHTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderMerchResponseTO.class);

    public static final int ORDERMERCH1_ID = START_SEQ + 8;
    public static final int NOT_FOUND = 10;

    public static final OrderMerch ORDER_DATA_1 = new OrderMerch(ORDERMERCH1_ID, order1, merch1, 100f, 2);
    public static final OrderMerch ORDER_DATA_2 = new OrderMerch(ORDERMERCH1_ID + 1, order1, merch2, 500f, 5);

    public static List<OrderMerch> orderMerchList = Arrays.asList(ORDER_DATA_1, ORDER_DATA_2);

    public static OrderMerch getNewWithOrder() {
        return new OrderMerch(null, order1, merch1, 100f, 1);
    }
    public static OrderMerch getNew() {
        return new OrderMerch(null, new Order(), merch1, 100f, 1);
    }

    public static OrderMerch getUpdated() {
        OrderMerch updated = new OrderMerch(ORDER_DATA_1);
        updated.setPrice(5f);
        updated.setCount(2000);
        return updated;
    }
}
