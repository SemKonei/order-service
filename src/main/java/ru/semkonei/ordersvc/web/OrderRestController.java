package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.semkonei.ordersvc.model.*;
import ru.semkonei.ordersvc.service.OrderService;
import ru.semkonei.ordersvc.web.to.OrderResponseTO;
import ru.semkonei.ordersvc.util.SecurityUtil;
import ru.semkonei.ordersvc.util.toUtils.OrderUtil;

import java.util.List;

@RestController
@RequestMapping(value = OrderRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    static final String REST_URL = "/rest/orders/";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderResponseTO get(@PathVariable Integer id) {
        log.info("get order {} for user {}", id, SecurityUtil.authUserId());
        return OrderUtil.createTo(orderService.getWithOM(id, SecurityUtil.authUserId()));
    }

    @GetMapping
    public List<OrderResponseTO> getAll() {
        log.info("get all orders for user {}", SecurityUtil.authUserId());
        return OrderUtil.getTos(orderService.getAll(SecurityUtil.authUserId()));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestParam OrderStatus status, @PathVariable Integer id) {
        log.info("update status for order {} ", id);
        Order order = orderService.get(id, SecurityUtil.authUserId());
        orderService.update(OrderUtil.updateFromTo(order, status), SecurityUtil.authUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete order {}", id);
        orderService.delete(id, SecurityUtil.authUserId());
    }
}
