package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.repository.OrderMerchRepository;

import java.util.List;
import java.util.Objects;

@Repository
public class DataJpaOrderMerchRepository implements OrderMerchRepository {

    CrudOrderRepository orderRepository;
    CrudMerchRepository merchRepository;
    CrudOrderMerchRepository orderMerchRepository;

    @Autowired
    public DataJpaOrderMerchRepository(CrudOrderMerchRepository orderMerchRepository, CrudOrderRepository orderRepository, CrudMerchRepository merchRepository) {
        this.orderMerchRepository = orderMerchRepository;
        this.orderRepository = orderRepository;
        this.merchRepository = merchRepository;
    }

    @Override
    public OrderMerch save(OrderMerch orderMerch, Integer orderId, Integer userId) {
        if (!orderMerch.isNew() && get(orderMerch.id(), userId) == null) {
            return null;
        } else {
            orderMerch.setOrder(orderRepository.getOne(orderId));
        }
        return orderMerchRepository.save(orderMerch);
    }

    @Override
    public OrderMerch get(Integer id, Integer userId) {
        return orderMerchRepository.findById(id)
                .filter(orderMerch -> Objects.equals(orderMerch.getOrder().getUser().getId(), userId))
                .orElse(null);
    }

    @Override
    public List<OrderMerch> getAllForAll() {
        return orderMerchRepository.findAll();
    }

    @Override
    public List<OrderMerch> getAll(Integer orderId, Integer userId) {
        return orderMerchRepository.getAll(orderId, userId);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return orderMerchRepository.delete(id, userId) != 0;
    }
}
