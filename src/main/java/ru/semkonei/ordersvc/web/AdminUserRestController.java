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
import ru.semkonei.ordersvc.util.toUtils.UserUtil;
import ru.semkonei.ordersvc.web.to.UserRequestTO;
import ru.semkonei.ordersvc.web.to.UserResponseTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {

    static final String REST_URL = "/rest/admin/user/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public AdminUserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseTO> create(@RequestBody UserRequestTO userTO) {
        log.info("create {}", userTO);
        User created = service.create(UserUtil.getFromTo(userTO));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(UserUtil.createTo(created));
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<UserResponseTO> update(@RequestBody UserRequestTO userTO, @PathVariable Integer userId) {
        log.info("update {} with id={}", userTO, userId);
        User user = service.get(userId);
        user = service.update(UserUtil.updateFromTo(user, userTO));
        return ResponseEntity.ok(UserUtil.createTo(user));
    }

    @GetMapping("/{userId}")
    public UserResponseTO get(@PathVariable Integer userId) {
        log.info("get user {}", userId);
        return  UserUtil.createTo(service.get(userId));
    }

    @GetMapping
    public List<UserResponseTO> getAll() {
        log.info("get all users");
        return UserUtil.getTos(service.getAll());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        log.info("delete user {}", userId);
        service.delete(userId);
    }
}
