package ru.semkonei.ordersvc.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.service.MerchService;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import ru.semkonei.ordersvc.util.toUtils.MerchUtil;
import ru.semkonei.ordersvc.web.json.JsonUtil;
import ru.semkonei.ordersvc.web.to.MerchResponseTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;
import static ru.semkonei.ordersvc.web.MerchRestController.REST_URL;

class MerchRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MerchService merchService;

    @Test
    void create() throws Exception {
        Merch newMerch = getNew();
        ResultActions action = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMerch)));
        Merch created = MERCH_MATCHER.readFromJson(action);
        int newId = created.id();
        newMerch.setId(newId);
        MERCH_MATCHER.assertMatch(created, newMerch);
        MERCH_MATCHER.assertMatch(merchService.get(newId), newMerch);
    }

    @Test
    void getUsers() throws Exception {
        perform(get(REST_URL + MERCH1_ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MERCH_MATCHER.contentJson(merch1));
    }

    @Test
    void getAll() throws Exception {
        perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MERCH_MATCHER.contentJson(merchList));
    }

    @Test
    void update() throws Exception {
        Merch updated = getUpdated();
        MerchResponseTO merchResponseTO = MerchUtil.createTo(updated);
        merchResponseTO.setId(null);
        perform(put(REST_URL + MERCH1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(merchResponseTO)))
                .andExpect(status().isNoContent());

        MERCH_MATCHER.assertMatch(merchService.get(MERCH1_ID), updated);
    }

    @Test
    public void deleteUser() throws Exception {
        perform(delete(REST_URL + MERCH1_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> merchService.get(MERCH1_ID));
    }
}