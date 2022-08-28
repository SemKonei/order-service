package ru.semkonei.ordersvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.testdata.OrderMerchTestData;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import ru.semkonei.ordersvc.web.to.OrderMerchRequestTO;
import ru.semkonei.ordersvc.web.to.OrderMerchRequestTOTest;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.ORDERMERCH1_ID;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.ORDERMERCH_MATCHER;
import static ru.semkonei.ordersvc.testdata.OrderTestData.ORDER1_ID;
import static ru.semkonei.ordersvc.testdata.UserTestData.USER_ID;

public class OrderDataServiceTest  extends AbstractServiceTest {

    @Autowired
    private OrderDataService orderDataService;
    @Autowired
    private OrderMerchService orderMerchService;

    @Test
    public void saveAllToNewOrder() {
        List<OrderMerch> newOrderMerchList = OrderMerchTestData.getNews();
        List<OrderMerchRequestTO> omToList = new ArrayList<>();
        newOrderMerchList.forEach(x-> omToList.add(new OrderMerchRequestTOTest(x)));
        List<OrderMerch>  createdList = orderDataService.saveAllToNewOrder(omToList, USER_ID);
        ORDERMERCH_MATCHER.assertMatch(createdList, newOrderMerchList);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.getAll(createdList.get(0).getOrder().getId(), USER_ID), newOrderMerchList);
    }

    @Test
    public void saveToNewOrder() {
        OrderMerch newOrderMerch = OrderMerchTestData.getNew();
        OrderMerch created = orderDataService.saveToNewOrder(new OrderMerchRequestTOTest(newOrderMerch), USER_ID);
        newOrderMerch.setId(created.getId());
        ORDERMERCH_MATCHER.assertMatch(created, newOrderMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(created.getId(), created.getOrder().getId(), USER_ID), newOrderMerch);
    }

    @Test
    public void deleteOrderItems() {
        orderDataService.deleteOrderItem(ORDERMERCH1_ID, ORDER1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> orderMerchService.get(ORDERMERCH1_ID, ORDER1_ID, USER_ID));
    }

    @Test
    public void deleteNotFoundOrderItems() {
        assertThrows(NotFoundException.class, () -> orderDataService.deleteOrderItem(NOT_FOUND, ORDER1_ID, USER_ID));
    }

    @Test
    public void updateOrderItem() {
        OrderMerch newItem = OrderMerchTestData.getUpdated();
        OrderMerch updatedItem = orderDataService.updateOrderItem(new OrderMerchRequestTOTest(newItem), newItem.id(), USER_ID);
        ORDERMERCH_MATCHER.assertMatch(updatedItem, newItem);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(updatedItem.getId(), updatedItem.getOrder().getId(), USER_ID), newItem);
    }

    @Test
    public void updateOrderItemList() {
        List<OrderMerch> newItemList = OrderMerchTestData.getUpdatedList();
        List<OrderMerchResponseTO> omToList = new ArrayList<>();
        newItemList.forEach(x-> omToList.add(new OrderMerchResponseTO(x)));
        List<OrderMerch>  updatedItemList = orderDataService.updateOrderItemList(omToList, newItemList.get(0).getOrder().getId(), USER_ID);
        ORDERMERCH_MATCHER.assertMatch(updatedItemList, newItemList);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.getAll(updatedItemList.get(0).getOrder().getId(), USER_ID), newItemList);

    }
}