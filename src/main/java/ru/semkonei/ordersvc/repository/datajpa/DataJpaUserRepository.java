package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.semkonei.ordersvc.model.User;
import ru.semkonei.ordersvc.repository.UserRepository;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {

    private final Sort SORTED_NAME_EMAIL = Sort.by(Sort.Direction.ASC,"name", "email");

    private CrudUserRepository userRepository;

    public DataJpaUserRepository(CrudUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll(SORTED_NAME_EMAIL);
    }

    @Override
    public boolean delete(Integer id) {
        return userRepository.delete(id) != 0;
    }
}
