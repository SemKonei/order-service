package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.service.UserService;
import ru.semkonei.ordersvc.util.SecurityUtil;
import ru.semkonei.ordersvc.web.to.UserRequestTO;
import ru.semkonei.ordersvc.web.to.UserResponseTO;
import ru.semkonei.ordersvc.util.toUtils.UserUtil;

import java.net.URI;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    static final String REST_URL = "/rest/users/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseTO> create(@RequestBody UserRequestTO userTO) {
        log.info("create {}", userTO);
        User created = service.create(UserUtil.createNewFromTo(userTO));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(UserUtil.createTo(created));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UserResponseTO> update(@RequestBody UserRequestTO userTO) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with id={}", userTO, userId);
        User user = UserUtil.updateFromTo(service.get(userId), userTO);
        return ResponseEntity.ok(UserUtil.createTo(service.update(user)));
    }

    @GetMapping()
    public UserResponseTO get() {
        int userId = SecurityUtil.authUserId();
        log.info("get user {}", userId);
        return  UserUtil.createTo(service.get(userId));
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        int userId = SecurityUtil.authUserId();
        log.info("delete user {}", userId);
        service.delete(userId);
    }
}
