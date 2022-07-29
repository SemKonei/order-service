package ru.semkonei.ordersvc.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.service.UserService;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import ru.semkonei.ordersvc.web.json.JsonUtil;
import ru.semkonei.ordersvc.web.to.UserRequestTOTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.semkonei.ordersvc.testdata.UserTestData.*;
import static ru.semkonei.ordersvc.web.UserRestController.REST_URL;

class UserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void create() throws Exception {
        User newUser = getNew();
        UserRequestTOTest userResponseTO = new UserRequestTOTest(newUser);
        ResultActions action = perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userResponseTO)));
        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        UserRequestTOTest userResponseTO = new UserRequestTOTest(updated);
        perform(put(REST_URL + USER_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userResponseTO)))
                .andDo(print())
                .andExpect(status().isOk());

        USER_MATCHER.assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    void getUsers() throws Exception {
        perform(get(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getAll() throws Exception {
        perform(get(REST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(userList));
    }

    @Test
    public void deleteUser() throws Exception {
        perform(delete(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }
}