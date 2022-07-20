package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.repository.OrderMerchRepository;
import ru.semkonei.ordersvc.util.SecurityUtil;

import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.checkNew;
import static ru.semkonei.ordersvc.util.ValidationUtil.checkNotFoundWithId;


@Service
public class OrderMerchService {

    OrderMerchRepository repository;

    @Autowired
    public OrderMerchService(OrderMerchRepository repository) {
        this.repository = repository;
    }

    public OrderMerch create(OrderMerch orderMerch, Integer orderId, Integer userId) {
        Assert.notNull(orderMerch, "OrderMerch must not be null!");
        checkNew(orderMerch);
        return repository.save(orderMerch, orderId, userId);
    }

    public OrderMerch get(Integer id, Integer userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Transactional
    public List<OrderMerch> getAll(Integer orderId, Integer userId) {
        return repository.getAll(orderId, userId);
    }
 /*   @Transactional
    public List<OrderMerch> getAllForAll() {
        return repository.getAllForAll();
    }*/

    public OrderMerch update(OrderMerch orderMerch, Integer orderId, Integer userId) {
        Assert.notNull(orderMerch, "OrderMerch must not be null!");
        return checkNotFoundWithId(repository.save(orderMerch, orderId, userId), orderMerch.id());
    }

    public boolean delete(Integer id, Integer userId) {
        return checkNotFoundWithId(repository.delete(id, userId), id);
    }
}
