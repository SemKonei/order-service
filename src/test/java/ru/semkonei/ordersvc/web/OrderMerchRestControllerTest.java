package ru.semkonei.ordersvc.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.service.OrderDataService;
import ru.semkonei.ordersvc.service.OrderMerchService;
import ru.semkonei.ordersvc.util.SecurityUtil;
import ru.semkonei.ordersvc.util.toUtils.OrderMerchUtil;
import ru.semkonei.ordersvc.web.json.JsonUtil;
import ru.semkonei.ordersvc.web.to.OrderMerchRequestTOTEst;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.getNew;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderTestData.ORDER1_ID;
import static ru.semkonei.ordersvc.web.OrderMerchRestController.REST_URL;

class OrderMerchRestControllerTest extends AbstractControllerTest {

    @Autowired
    private OrderMerchService orderMerchService;
    @Autowired
    private OrderDataService orderDataService;

    @Test
    void save() throws Exception {
        OrderMerch newMerch = getNew();
        OrderMerchRequestTOTEst orderMerchRequestTO = new OrderMerchRequestTOTEst(newMerch);
        ResultActions action = perform(
                post(REST_URL +
                        "?merchId=" + newMerch.getId() +
                        "&count=" + getNew().getCount())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(orderMerchRequestTO)))
                .andDo(print());
        OrderMerchResponseTO OMto = ORDERMERCHTO_MATCHER.readFromJson(action);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), SecurityUtil.authUserId()), newMerch);
    }

    @Test
    void saveWithOldOrder() throws Exception {
        OrderMerch newMerch = getNew();
        OrderMerchRequestTOTEst orderMerchRequestTO = new OrderMerchRequestTOTEst(newMerch);
        ResultActions action = perform(
                post(REST_URL +
                        "?merchId=" + newMerch.getMerch().getId() +
                        "&count=" + newMerch.getCount()+
                        "&orderId=" + newMerch.getOrder().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(orderMerchRequestTO)))
                .andDo(print());
        OrderMerchResponseTO OMto = ORDERMERCHTO_MATCHER.readFromJson(action);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), SecurityUtil.authUserId()), newMerch);
    }
    @Test
    void saveWithOldOrder2() throws Exception {
        OrderMerch newMerch = getNew();
        OrderMerchRequestTOTEst orderMerchRequestTO = new OrderMerchRequestTOTEst(newMerch);
        ResultActions action = perform(
                post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(orderMerchRequestTO)))
                .andDo(print());
        OrderMerchResponseTO OMto = ORDERMERCHTO_MATCHER.readFromJson(action);
        OrderMerch created = new OrderMerch(OMto.getId(), new Order(OMto.getOrderId()), new Merch(OMto.getMerchId()),
                OMto.getPrice(), OMto.getCount());
        int newId = created.id();
        newMerch.setId(newId);
        ORDERMERCH_MATCHER.assertMatch(created, newMerch);
        ORDERMERCH_MATCHER.assertMatch(orderMerchService.get(newId, created.getOrder().getId(), SecurityUtil.authUserId()), newMerch);
    }

    @Test
    void getOrderMerch() throws Exception {
        perform(get(REST_URL + ORDERMERCH1_ID + "?orderId=" + ORDER1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(ORDERMERCHTO_MATCHER.contentJson(OrderMerchUtil.createTo(ORDER_DATA_1)));
    }

    @Test
    void updateOrderMerch() {
    }

    @Test
    void deleteOM() {
    }
}