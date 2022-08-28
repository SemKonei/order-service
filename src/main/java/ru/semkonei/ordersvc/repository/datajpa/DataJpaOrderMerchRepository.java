package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public OrderMerch save(OrderMerch orderMerch, Integer userId) {
        return orderMerchRepository.save(orderMerch);
    }

    @Override
    @Transactional
    public List<OrderMerch> saveAll(List<OrderMerch> orderMerchList, Integer userId) {
        return orderMerchRepository.saveAll(orderMerchList);
    }

    @Override
    public OrderMerch get(Integer id, Integer orderId, Integer userId) {
        return orderMerchRepository.findById(id)
                .filter(orderMerch ->
                        Objects.equals(orderMerch.getOrder().getUser().getId(), userId) &&
                        Objects.equals(orderMerch.getOrder().getId(), orderId)
                )
                .orElse(null);
    }

    @Override
    public List<OrderMerch> getAll(Integer orderId, Integer userId) {
        return orderMerchRepository.getAll(orderId, userId);
    }

    @Override
    @Transactional
    public Integer delete(Integer id, Integer orderId, Integer userId) {
        Integer result = orderMerchRepository.delete(id, orderId, userId);
        return result != 0 ? result : null;
    }
}
