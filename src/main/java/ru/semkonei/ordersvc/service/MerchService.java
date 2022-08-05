package ru.semkonei.ordersvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.repository.MerchRepository;

import java.util.List;

import static ru.semkonei.ordersvc.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MerchService {

    MerchRepository repository;

    @Autowired
    public MerchService(MerchRepository repository) {
        this.repository = repository;
    }

    public Merch create(Merch merch) {
        Assert.notNull(merch, "Merch must not be null!");
        return repository.save(merch);
    }

    public Merch get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Merch> getAll() {
        return repository.getAll();
    }

    public Merch update(Merch merch) {
        Assert.notNull(merch, "Merch must not be null!");
        return checkNotFoundWithId(repository.save(merch), merch.id());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
