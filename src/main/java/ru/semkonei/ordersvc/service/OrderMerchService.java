package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.repository.OrderMerchRepository;

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

    public OrderMerch create(OrderMerch orderMerch) {
        Assert.notNull(orderMerch, "OrderMerch must not be null!");
        checkNew(orderMerch);
        return repository.save(orderMerch);
    }

    public OrderMerch get(Integer id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    public List<OrderMerch> getAll(Integer orderId) {
        return repository.getAll(orderId);
    }

    public OrderMerch update(OrderMerch orderMerch, Integer orderId, Integer merchId) {
        Assert.notNull(orderMerch, "OrderMerch must not be null!");
        return checkNotFoundWithId(repository.save(orderMerch), orderMerch.id());
    }

    public boolean delete(Integer id) {
        return checkNotFoundWithId(repository.delete(id), id);
    }
}
