package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User get(Integer id);

    User getByEmail(String email);

    List<User> getAll();

    boolean delete(Integer id);
}
