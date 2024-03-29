package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.repository.OrderMerchRepository;
import ru.semkonei.ordersvc.util.exception.NotFoundException;

import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.checkNotFoundWithId;


@Service
public class OrderMerchService {

    private final OrderMerchRepository repository;

    @Autowired
    public OrderMerchService(OrderMerchRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OrderMerch create(OrderMerch orderMerch, Merch merch, Order order, Integer userId) {
        Assert.notNull(orderMerch, "OrderItem must not be null!");
        if (!orderMerch.isNew() && get(orderMerch.id(), order.getId(), userId) == null) {
            return null;
        }
        orderMerch.setOrder(order);
        orderMerch.setMerch(merch);
        return repository.save(orderMerch, userId);
    }

    @Transactional
    public List<OrderMerch> createAll(List<OrderMerch> orderItems, Integer userId) {
        Assert.notNull(orderItems, "OrderItem must not be null!");
        return repository.saveAll(orderItems, userId);
    }

    public OrderMerch get(Integer id, Integer orderId, Integer userId) {
        return checkNotFoundWithId(repository.get(id, orderId, userId), id);
    }

    public List<OrderMerch> getAll(Integer orderId, Integer userId) {
        return repository.getAll(orderId, userId);
    }

    @Transactional
    public OrderMerch update(OrderMerch orderMerch, Integer userId) {
        Assert.notNull(orderMerch, "OrderItem must not be null!");
        return checkNotFoundWithId(repository.save(orderMerch, userId), orderMerch.id());
    }

    @Transactional
    public List<OrderMerch> updateList(List<OrderMerch> orderMerch, Integer userId) {
        Assert.notNull(orderMerch, "OrderItemsList must not be null!");
        orderMerch = repository.saveAll(orderMerch, userId);
        if (orderMerch.isEmpty()) throw new NotFoundException("Order items not found.");
        return orderMerch;
    }

    @Transactional
    public void delete(Integer id, Integer orderId, Integer userId) {
        checkNotFoundWithId(repository.delete(id, orderId, userId), id);
    }
}
