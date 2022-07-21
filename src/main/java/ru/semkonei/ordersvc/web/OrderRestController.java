package ru.semkonei.ordersvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.semkonei.ordersvc.model.*;
import ru.semkonei.ordersvc.service.MerchService;
import ru.semkonei.ordersvc.service.OrderDataService;
import ru.semkonei.ordersvc.service.OrderMerchService;
import ru.semkonei.ordersvc.service.OrderService;
import ru.semkonei.ordersvc.to.MerchTO;
import ru.semkonei.ordersvc.to.OrderMerchTO;
import ru.semkonei.ordersvc.to.OrderTO;
import ru.semkonei.ordersvc.util.SecurityUtil;
import ru.semkonei.ordersvc.util.toUtils.OrderUtil;

import java.net.URI;
import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = OrderRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    static final String REST_URL = "rest/order";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OrderDataService orderDataService;
    private final OrderService orderService;
    private final OrderMerchService orderMerchService;

    @Autowired
    public OrderRestController(OrderDataService orderDataService,
                               OrderService orderService, OrderMerchService orderMerchService) {
        this.orderDataService = orderDataService;
        this.orderService = orderService;
        this.orderMerchService = orderMerchService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> save(@RequestBody OrderMerchTO orderMerchTO) {
        log.info("add {} to new order", orderMerchTO);
        return getOrderResponseEntity(
                orderDataService.create(
                        orderMerchTO.getMerchId(),
                        orderMerchTO.getCount(),
                        null,
                        SecurityUtil.authUserId()));
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> save(@RequestBody OrderMerchTO orderMerchTO, @PathVariable Integer id) {
        log.info("add {} to order {}", orderMerchTO, id);
        return getOrderResponseEntity(
                orderDataService.create(
                        orderMerchTO.getMerchId(),
                        orderMerchTO.getCount(),
                        id,
                        SecurityUtil.authUserId()));
    }

    private ResponseEntity<Order> getOrderResponseEntity(Order order) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(order.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(order);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Integer id) {
        log.info("get order {} for user {}", id,SecurityUtil.authUserId());
        //return  orderService.get(id, SecurityUtil.authUserId());
        return  orderService.getWithOM(id, SecurityUtil.authUserId());
    }

    @GetMapping
    public List<OrderTO> getAll() {
        log.info("get all orders for user {}",SecurityUtil.authUserId());
        return OrderUtil.getTos(orderService.getAll(SecurityUtil.authUserId()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@RequestParam OrderStatus status, @PathVariable Integer id) {
        log.info("update status for order {} ", id);
        Order order = orderService.get(id, SecurityUtil.authUserId());
        order.setStatus(status);
        orderService.update(order, SecurityUtil.authUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete order {}", id);
        orderService.delete(id, SecurityUtil.authUserId());
    }



    //TODO: DELETE THIS (TEST ONLY)
    /*@GetMapping("/details/all-{orderId}")
    public List<OrderMerch> getAllMerch(@PathVariable Integer orderId) {
        log.info("get all merch for user {}",SecurityUtil.authUserId());
        return orderDataService.getAllMerches(orderId, SecurityUtil.authUserId());
    }
    @GetMapping("/details/{orderMerchId}")
    public OrderMerch getOrderMerch(@PathVariable Integer orderMerchId) {
        log.info("get merch from order {}",orderMerchId);
        return orderMerchService.get(orderMerchId, SecurityUtil.authUserId());
    }
    @GetMapping("/details/allforall")
    public List<OrderMerch> getAllMerchForAll() {
        log.info("get ALL merch in ALL orders");
        return orderDataService.getAllMerchesForAll();
    }
    @DeleteMapping("/details/{orderMerchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOM(@PathVariable Integer orderMerchId) {
        log.info("delete orderMerch {}", orderMerchId);
        orderMerchService.delete(orderMerchId, SecurityUtil.authUserId());
    }*/
}
