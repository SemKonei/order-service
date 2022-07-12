package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.repository.OrderMerchRepository;

import java.util.List;

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
    public OrderMerch save(OrderMerch orderMerch) {
        if (!orderMerch.isNew() && get(orderMerch.id()) == null) {
            return null;
        }
        return orderMerchRepository.save(orderMerch);
    }

    @Override
    public OrderMerch get(Integer id) {
        return orderMerchRepository.get(id);
    }

    @Override
    public List<OrderMerch> getAll(Integer orderId) {
        return orderMerchRepository.getAll(orderId);
    }

    @Override
    public boolean delete(Integer id) {
        return orderMerchRepository.delete(id) != 0;
    }
}
