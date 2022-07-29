package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.service.MerchService;
import ru.semkonei.ordersvc.service.OrderDataService;
import ru.semkonei.ordersvc.service.OrderMerchService;
import ru.semkonei.ordersvc.web.to.OrderMerchRequestTO;
import ru.semkonei.ordersvc.web.to.OrderMerchResponseTO;
import ru.semkonei.ordersvc.util.SecurityUtil;
import ru.semkonei.ordersvc.util.toUtils.OrderMerchUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = OrderMerchRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderMerchRestController {

    static final String REST_URL = OrderRestController.REST_URL + "details/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OrderDataService orderDataService;
    private final OrderMerchService orderMerchService;
    private final MerchService merchService;

    @Autowired
    public OrderMerchRestController(OrderDataService orderDataService, OrderMerchService orderMerchService, MerchService merchService) {
        this.orderDataService = orderDataService;
        this.orderMerchService = orderMerchService;
        this.merchService = merchService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderMerchResponseTO> save(@RequestBody OrderMerchRequestTO orderMerchTO) {
        log.info("add OrderMerch {} to Order {}", orderMerchTO.getMerchId(), orderMerchTO.getOrderId());
        OrderMerch orderMerch = orderDataService.create(
                orderMerchTO.getMerchId(),
                orderMerchTO.getCount(),
                orderMerchTO.getOrderId(),
                SecurityUtil.authUserId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(orderMerch.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(OrderMerchUtil.createTo(orderMerch));
    }

    @PutMapping(value = "/{orderMerchId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderMerch(@RequestBody OrderMerchRequestTO orderMerchTO, @PathVariable Integer orderMerchId) {
        log.info("update OrderMerch {} for Order {}", orderMerchId, orderMerchTO.getOrderId());
        OrderMerch orderMerch = orderMerchService.get(orderMerchId, orderMerchTO.getOrderId(), SecurityUtil.authUserId());
        orderMerchService.update(
                OrderMerchUtil.updateFromTo(orderMerch, orderMerchTO),
                SecurityUtil.authUserId());
    }

    @GetMapping("/{orderMerchId}")
    public OrderMerchResponseTO getOrderMerch(@PathVariable Integer orderMerchId, @RequestParam Integer orderId) {
        log.info("get OrderMerch {} from Order {}", orderMerchId, orderId);
        return OrderMerchUtil.createTo(orderMerchService.get(orderMerchId, orderId, SecurityUtil.authUserId()));
    }

    @GetMapping()
    public List<OrderMerchResponseTO> getOrderMerch(@RequestParam Integer orderId) {
        log.info("get items from Order {}", orderId);
        return OrderMerchUtil.getTos(orderMerchService.getAll(orderId, SecurityUtil.authUserId()));
    }

    @DeleteMapping("/{orderMerchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOM(@PathVariable Integer orderMerchId, @RequestParam Integer orderId) {
        log.info("delete OrderMerch from Order {}", orderMerchId);
        orderMerchService.delete(orderMerchId, orderId, SecurityUtil.authUserId());
    }

}
