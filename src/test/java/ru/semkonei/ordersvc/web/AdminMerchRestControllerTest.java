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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.semkonei.ordersvc.TestUtil.userHttpBasic;
import static ru.semkonei.ordersvc.testdata.MerchTestData.*;
import static ru.semkonei.ordersvc.testdata.UserTestData.admin;
import static ru.semkonei.ordersvc.web.AdminMerchRestController.REST_URL;

class AdminMerchRestControllerTest extends AbstractControllerTest {

    @Autowired
    private MerchService merchService;

    @Test
    void create() throws Exception {
        Merch newMerch = getNew();
        ResultActions action = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMerch))
                .with(userHttpBasic(admin)));
        Merch created = MERCH_MATCHER.readFromJson(action);
        int newId = created.id();
        newMerch.setId(newId);
        MERCH_MATCHER.assertMatch(created, newMerch);
        MERCH_MATCHER.assertMatch(merchService.get(newId), newMerch);
    }

    @Test
    void update() throws Exception {
        Merch updatedMerch = getUpdated();
        MerchResponseTO merchResponseTO = MerchUtil.createTo(updatedMerch);
        merchResponseTO.setId(null);
        ResultActions action = perform(put(REST_URL + MERCH1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(merchResponseTO))
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk());

        Merch updated = MERCH_MATCHER.readFromJson(action);
        int newId = updated.id();
        updatedMerch.setId(newId);
        MERCH_MATCHER.assertMatch(updated, updatedMerch);
        MERCH_MATCHER.assertMatch(merchService.get(newId), updatedMerch);
    }

    @Test
    public void deleteMerch() throws Exception {
        perform(delete(REST_URL + MERCH1_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> merchService.get(MERCH1_ID));
    }
}