package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.service.UserService;
import ru.semkonei.ordersvc.web.to.UserRequestTO;
import ru.semkonei.ordersvc.web.to.UserResponseTO;

import java.util.List;

import static ru.semkonei.ordersvc.util.toUtils.UserUtil.*;

@RestController
@RequestMapping(value = AdminUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {

    static final String REST_URL = "/rest/admin/users/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public AdminUserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseTO> create(@RequestBody UserRequestTO userTO) {
        log.info("create {}", userTO);
        return ResponseEntity.ok(createTo(service.create(getFromTo(userTO))));
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseTO update(@RequestBody UserRequestTO userTO, @PathVariable Integer userId) {
        log.info("update {} with id={}", userTO, userId);
        User user = service.get(userId);
        return createTo(service.update(updateFromTo(user, userTO)));
    }

    @GetMapping("/{userId}")
    public UserResponseTO get(@PathVariable Integer userId) {
        log.info("get user {}", userId);
        return  createTo(service.get(userId));
    }

    @GetMapping
    public List<UserResponseTO> getAll() {
        log.info("get all users");
        return getTos(service.getAll());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        log.info("delete user {}", userId);
        service.delete(userId);
    }
}
