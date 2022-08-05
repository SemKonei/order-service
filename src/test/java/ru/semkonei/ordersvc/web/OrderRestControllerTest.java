package ru.semkonei.ordersvc.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderStatus;
import ru.semkonei.ordersvc.service.OrderService;
import ru.semkonei.ordersvc.util.exception.NotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.semkonei.ordersvc.TestUtil.userHttpBasic;
import static ru.semkonei.ordersvc.testdata.OrderTestData.*;
import static ru.semkonei.ordersvc.testdata.UserTestData.USER_ID;
import static ru.semkonei.ordersvc.testdata.UserTestData.user;
import static ru.semkonei.ordersvc.web.OrderRestController.REST_URL;

class OrderRestControllerTest extends AbstractControllerTest {

    @Autowired
    private OrderService orderService;

    @Test
    void getOrder() throws Exception {
        perform(get(REST_URL + ORDER1_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(ORDER_MATCHER.contentJson(order1));
    }

    @Test
    void getAll() throws Exception {
        perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(ORDER_MATCHER.contentJson(orderList));
    }

    @Test
    void updateStatus() throws Exception {
        Order updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + ORDER1_ID + "?status=" + OrderStatus.COMPLETED)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user)))
                .andExpect(status().isNoContent());
        ORDER_MATCHER.assertMatch(orderService.get(ORDER1_ID, USER_ID), updated);
    }

    @Test
    void deleteOrder() throws Exception {
        perform(delete(REST_URL + ORDER1_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user)))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> orderService.get(ORDER1_ID, USER_ID));
    }
}