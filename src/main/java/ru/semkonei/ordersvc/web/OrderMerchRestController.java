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

    static final String REST_URL = OrderRestController.REST_URL + "items/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OrderDataService orderDataService;
    private final OrderMerchService orderMerchService;

    @Autowired
    public OrderMerchRestController(OrderDataService orderDataService, OrderMerchService orderMerchService) {
        this.orderDataService = orderDataService;
        this.orderMerchService = orderMerchService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderMerchResponseTO> save(@RequestBody OrderMerchRequestTO orderMerchTO) {
        log.info("add OrderMerch {} to Order {}", orderMerchTO.getMerchId(), orderMerchTO.getOrderId());
        OrderMerch orderMerch = orderDataService.saveToNewOrder(orderMerchTO, SecurityUtil.authUserId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(orderMerch.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(OrderMerchUtil.createTo(orderMerch));
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderMerchResponseTO>> saveList(@RequestBody List<OrderMerchRequestTO> orderMerchTO) {
        log.info("add OrderItems to new Order");
        List<OrderMerch> orderMerchList = orderDataService.saveAllToNewOrder(orderMerchTO, SecurityUtil.authUserId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(orderMerchList.get(0).getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(OrderMerchUtil.getTos(orderMerchList));
    }

    @PutMapping(value = "/{orderMerchId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderMerchResponseTO update(@RequestBody OrderMerchRequestTO orderMerchTO, @PathVariable Integer orderMerchId) {
        log.info("update OrderMerch {} for Order {}", orderMerchId, orderMerchTO.getOrderId());
        return OrderMerchUtil.createTo(orderDataService.updateOrderItem(orderMerchTO, orderMerchId, SecurityUtil.authUserId()));
    }

    @PutMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderMerchResponseTO> updateList(@RequestBody  List<OrderMerchResponseTO> orderMerchTO, @RequestParam Integer orderId) {
        log.info("update OrderItems for Order {}",  orderId);
        return OrderMerchUtil.getTos(orderDataService.updateOrderItemList(orderMerchTO, orderId, SecurityUtil.authUserId()));
    }

    @GetMapping("/{orderMerchId}")
    public OrderMerchResponseTO get(@PathVariable Integer orderMerchId, @RequestParam Integer orderId) {
        log.info("get OrderMerch {} from Order {}", orderMerchId, orderId);
        return OrderMerchUtil.createTo(orderMerchService.get(orderMerchId, orderId, SecurityUtil.authUserId()));
    }

    @GetMapping()
    public List<OrderMerchResponseTO> getAll(@RequestParam Integer orderId) {
        log.info("get items from Order {}", orderId);
        return OrderMerchUtil.getTos(orderMerchService.getAll(orderId, SecurityUtil.authUserId()));
    }

    @DeleteMapping("/{orderMerchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer orderMerchId, @RequestParam Integer orderId) {
        log.info("delete OrderMerch from Order {}", orderMerchId);
        orderDataService.deleteOrderItem(orderMerchId, orderId, SecurityUtil.authUserId());
    }

}
