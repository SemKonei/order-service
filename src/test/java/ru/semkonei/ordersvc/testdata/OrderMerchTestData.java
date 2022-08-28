package ru.semkonei.ordersvc.testdata;

import ru.semkonei.ordersvc.MatcherFactory;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.semkonei.ordersvc.model.BaseEntity.START_SEQ;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderTestData.order1;

public class OrderMerchTestData {
    public static final MatcherFactory.Matcher<OrderMerch> ORDERMERCH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderMerch.class, "merch", "order");
    public static final MatcherFactory.Matcher<OrderMerchResponseTO> ORDERMERCHTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderMerchResponseTO.class);
    public static final MatcherFactory.Matcher<List> ORDERMERCHLISTTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(List.class);

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


    public static List<OrderMerch> getNews() {
        List<OrderMerch> orderItems = new ArrayList<>();
        Order newOrder = new Order(100010);
        orderItems.add(new OrderMerch(100011, newOrder, merch1, 100f, 1));
        orderItems.add(new OrderMerch(100012, newOrder, merch2, 500f, 2));
        return orderItems;
    }

    public static OrderMerch getUpdated() {
        OrderMerch updated = new OrderMerch(ORDER_DATA_1);
        updated.setPrice(5f);
        updated.setCount(2000);
        return updated;
    }

    public static List<OrderMerch> getUpdatedList() {
        List<OrderMerch> orderItems = new ArrayList<>();
        orderItems.add(new OrderMerch(ORDER_DATA_1));
        orderItems.add(new OrderMerch(ORDER_DATA_2));
        orderItems.get(0).setCount(2000);
        orderItems.get(1).setCount(1000);
        return orderItems;
    }
}
