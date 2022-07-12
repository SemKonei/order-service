package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.repository.OrderRepository;

import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.checkNew;
import static ru.semkonei.ordersvc.util.ValidationUtil.checkNotFoundWithId;

@Service
public class OrderService {

    OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order, int userId) {
        Assert.notNull(order, "Order must not be null!");
        checkNew(order);
        return repository.save(order, userId);
    }

    public Order get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Order> getAll(int userId) {
        return repository.getAll(userId);
    }

    public  Order update(Order order, int userId) {
        Assert.notNull(order, "Order must not be null!");
        return checkNotFoundWithId(repository.save(order, userId), order.id());
    }

    public boolean delete(int id, int userId) {
        return checkNotFoundWithId(repository.delete(id, userId), id);
    }
}
