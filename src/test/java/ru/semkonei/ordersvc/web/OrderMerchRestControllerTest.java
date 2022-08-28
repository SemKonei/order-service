package ru.semkonei.ordersvc.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.service.OrderMerchService;
import ru.semkonei.ordersvc.testdata.OrderMerchTestData;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import ru.semkonei.ordersvc.util.toUtils.OrderMerchUtil;
import ru.semkonei.ordersvc.web.json.JsonUtil;
import ru.semkonei.ordersvc.web.to.OrderMerchRequestTOTest;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;
import ru.semkonei.ordersvc.web.to.OrderMerchTOTest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.semkonei.ordersvc.TestUtil.userHttpBasic;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.getNew;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderTestData.*;
import static ru.semkonei.ordersvc.testdata.UserTestData.*;
import static ru.semkonei.ordersvc.web.OrderMerchRestController.REST_URL;

class OrderMerchRestControllerTest extends AbstractControllerTest {

    @Autowired
    private OrderMerchService orderMerchService;

    @Test
    void saveNew() throws Exception {
        OrderMerch newMerch = getNew();
        OrderMerchRequestTOTest orderMerchRequestTO = new OrderMerchRequestTOTest(newMerch);
        OrderMerchResponseTO OMto = getOrderMerchResponseTO(orderMerchRequestTO);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), USER_ID), newMerch);
    }

    @Test
    void saveAll() throws Exception {
        List<OrderMerch> newItems = getNews();
        List<OrderMerchRequestTOTest> orderMerchRequestTOList = new ArrayList<>();
        newItems.forEach(x -> orderMerchRequestTOList.add(new OrderMerchRequestTOTest(x)));
        ResultActions action = perform(
                post(REST_URL + "list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(orderMerchRequestTOList))
                        .with(userHttpBasic(user)))
                .andDo(print());

        List<LinkedHashMap<String, Integer>> orderMerchResponseTOS = ORDERMERCHLISTTO_MATCHER.readFromJson(action);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.getAll((orderMerchResponseTOS.get(0)).get("orderId"), USER_ID), newItems);
    }

    @Test
    void saveNewWithoutOrder() throws Exception {
        OrderMerch newOrderMerch = getNew();
        newOrderMerch.setOrder(null);
        OrderMerchRequestTOTest orderMerchRequestTO = new OrderMerchRequestTOTest(newOrderMerch.getMerch().id(), null,
                newOrderMerch.getPrice(), newOrderMerch.getCount());
        OrderMerchResponseTO OMto = getOrderMerchResponseTO(orderMerchRequestTO);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newOrderMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newOrderMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), USER_ID), newOrderMerch);
    }

    @Test
    void saveWithOrder() throws Exception {
        OrderMerch newMerch = getNewWithOrder();
        OrderMerchRequestTOTest orderMerchRequestTO = new OrderMerchRequestTOTest(newMerch);
        OrderMerchResponseTO OMto = getOrderMerchResponseTO(orderMerchRequestTO);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), USER_ID), newMerch);
    }

    private OrderMerchResponseTO getOrderMerchResponseTO(OrderMerchRequestTOTest orderMerchRequestTO) throws Exception {
        ResultActions action = perform(
                post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(orderMerchRequestTO))
                        .with(userHttpBasic(user)))
                .andDo(print());
        return ORDERMERCHTO_MATCHER.readFromJson(action);
    }

    @Test
    void getOrderMerch() throws Exception {
        perform(get(REST_URL + ORDERMERCH1_ID + "?orderId=" + ORDER1_ID)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(ORDERMERCHTO_MATCHER.contentJson(OrderMerchUtil.createTo(ORDER_DATA_1)));
    }

    @Test
    void updateOrderMerch() throws Exception {
        OrderMerch newMerch = OrderMerchTestData.getUpdated();
        OrderMerchRequestTOTest orderMerchRequestTO = new OrderMerchRequestTOTest(newMerch);
        ResultActions action = perform(
                put(REST_URL + newMerch.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(orderMerchRequestTO))
                        .with(userHttpBasic(user)))
                .andDo(print());
        OrderMerchResponseTO OMto = ORDERMERCHTO_MATCHER.readFromJson(action);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), USER_ID), newMerch);
    }

    @Test
    void updateOrderMerchList() throws Exception {
        List<OrderMerch> updatedList = OrderMerchTestData.getUpdatedList();
        List<OrderMerchTOTest> updatedOMTOList = new ArrayList<>();
        updatedList.forEach(orderMerch -> updatedOMTOList.add(new OrderMerchTOTest(orderMerch)));
        ResultActions action = perform(
                put(REST_URL + "list" + "?orderId=" + updatedOMTOList.get(0).getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updatedOMTOList))
                        .with(userHttpBasic(user)))
                .andDo(print());

        List<LinkedHashMap<String, Integer>> orderMerchResponseTOS = ORDERMERCHLISTTO_MATCHER.readFromJson(action);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.getAll((orderMerchResponseTOS.get(0)).get("orderId"), USER_ID), updatedList);
    }

    @Test
    void deleteOM() throws Exception {
        perform(delete(OrderRestController.REST_URL + ORDER1_ID + "?orderId=" + ORDER1_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user)))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> orderMerchService.get(ORDERMERCH1_ID, ORDER1_ID, USER_ID));
    }
}