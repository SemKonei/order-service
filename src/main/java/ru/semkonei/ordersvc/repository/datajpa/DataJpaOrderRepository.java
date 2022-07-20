package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Order;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.repository.OrderRepository;

import java.util.List;
import java.util.Objects;

@Repository
public class DataJpaOrderRepository implements OrderRepository {

    CrudOrderRepository orderRepository;
    CrudUserRepository userRepository;

    public DataJpaOrderRepository(CrudOrderRepository orderRepository, CrudUserRepository userRepositoryRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepositoryRepository;
    }

    @Override
    @Transactional
    public Order save(Order order, Integer userId) {
        if (!order.isNew() && get(order.id(), userId) == null) {
            return null;
        } else {
            User user = userRepository.getOne(userId);
            order.setUser(user);
        }
        return orderRepository.save(order);
    }

    @Override
    public Order get(Integer id, Integer userId) {
        return orderRepository.findById(id)
                .filter(order -> Objects.equals(order.getUser().getId(), userId))
                .orElse(null);
    }

    @Override
    public Order getInProcess(Integer userId) {
        return orderRepository.getInProcess(userId);
    }

    @Override
    public Order getWithOM(Integer id,Integer userId) {
        return orderRepository.getWithOM(id, userId);
    }

    @Override
    public List<Order> getAll(Integer userId) {
        return orderRepository.getAll(userId);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return orderRepository.delete(id, userId) != 0;
    }
}
