package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.repository.UserRepository;

import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.*;

@Service
public class UserService {

    UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "User must not be null!");
        checkNew(user);
        return repository.save(user);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User update(User user) {
        Assert.notNull(user, "User must not be null!");
        return checkNotFoundWithId(repository.save(user), user.id());
    }

    public boolean delete(int id) {
        return checkNotFoundWithId(repository.delete(id), id);
    }
}
