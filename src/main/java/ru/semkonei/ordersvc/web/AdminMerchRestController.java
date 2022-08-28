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
import ru.semkonei.ordersvc.web.to.MerchRequestTO;
import ru.semkonei.ordersvc.web.to.MerchResponseTO;
import ru.semkonei.ordersvc.util.toUtils.MerchUtil;

import java.net.URI;

@RestController
@RequestMapping(value = AdminMerchRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMerchRestController {

    static final String REST_URL = "/rest/admin/merches/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MerchService service;

    @Autowired
    public AdminMerchRestController(MerchService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MerchResponseTO> create(@RequestBody MerchRequestTO merchTO) {
        log.info("create {}", merchTO);
        Merch created = service.create(MerchUtil.getFromTo(merchTO));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(MerchUtil.createTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MerchResponseTO update(@RequestBody MerchRequestTO merchTO, @PathVariable Integer id) {
        log.info("update {} ", merchTO);
        Merch merch = service.get(id);
        return MerchUtil.createTo(service.update(MerchUtil.updateFromTo(merch, merchTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete merch {}", id);
        service.delete(id);
    }
}