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
import ru.semkonei.ordersvc.to.UserTO;
import ru.semkonei.ordersvc.util.toUtils.UserUtil;

import java.net.URI;
import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    static final String REST_URL = "rest/user";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTO> create(@RequestBody UserTO userTO) {
        log.info("create {}", userTO);
        User created = service.create(UserUtil.getFromTo(userTO));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(UserUtil.createTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTO userTO, @PathVariable Integer id) {
        log.info("update {} ", userTO);
        assureIdConsistent(userTO, id);
        service.update(UserUtil.getFromTo(userTO));
    }

    @GetMapping("/{id}")
    public UserTO get(@PathVariable Integer id) {
        log.info("get user {}", id);
        return  UserUtil.createTo(service.get(id));
    }

    @GetMapping
    public List<UserTO> getAll() {
        log.info("get all users");
        return UserUtil.getTos(service.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete user {}", id);
        service.delete(id);
    }
}
