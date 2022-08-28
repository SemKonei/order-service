package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.semkonei.ordersvc.service.MerchService;
import ru.semkonei.ordersvc.util.toUtils.MerchUtil;
import ru.semkonei.ordersvc.web.to.MerchResponseTO;

import java.util.List;

@RestController
@RequestMapping(value = MerchRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchRestController {

    static final String REST_URL = "/rest/merches/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MerchService service;

    @Autowired
    public MerchRestController(MerchService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public MerchResponseTO get(@PathVariable Integer id) {
        log.info("get merch {}", id);
        return  MerchUtil.createTo(service.get(id));
    }

    @GetMapping
    public List<MerchResponseTO> getAll() {
        log.info("get all merch");
        return MerchUtil.getTos(service.getAll());
    }
}
