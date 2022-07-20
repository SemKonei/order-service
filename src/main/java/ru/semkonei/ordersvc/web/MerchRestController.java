package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.service.MerchService;
import ru.semkonei.ordersvc.to.MerchTO;
import ru.semkonei.ordersvc.util.toUtils.MerchUtil;

import java.net.URI;
import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = MerchRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchRestController {

    static final String REST_URL = "rest/merch";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MerchService service;

    @Autowired
    public MerchRestController(MerchService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MerchTO> create(@RequestBody MerchTO merchTO) {
        log.info("create {}", merchTO);
        Merch created = service.create(MerchUtil.getFromTo(merchTO));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(MerchUtil.createTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody MerchTO merch, @PathVariable Integer id) {
        log.info("update {} ", merch);
        assureIdConsistent(merch, id);
        service.update(MerchUtil.getFromTo(merch));
    }

    @GetMapping("/{id}")
    public MerchTO get(@PathVariable Integer id) {
        log.info("get merch {}", id);
        return  MerchUtil.createTo(service.get(id));
    }

    @GetMapping
    public List<MerchTO> getAll() {
        log.info("get all merch");
        return MerchUtil.getTos(service.getAll());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete merch {}", id);
        service.delete(id);
    }
}