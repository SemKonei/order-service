package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.repository.MerchRepository;

import java.util.List;

@Repository
public class DataJpaMerchRepository implements MerchRepository {

    private final Sort SORTED_NAME = Sort.by(Sort.Direction.ASC,"name");

    CrudMerchRepository merchRepository;

    public DataJpaMerchRepository(CrudMerchRepository merchRepository) {
        this.merchRepository = merchRepository;
    }

    @Override
    public Merch save(Merch merch) {
        return merchRepository.save(merch);
    }

    @Override
    public Merch get(Integer id) {
        return merchRepository.findById(id).orElse(null);
    }

    @Override
    public List<Merch> getAll() {
        return merchRepository.findAll(SORTED_NAME);
    }

    @Override
    public boolean delete(Integer id) {
        return merchRepository.delete(id) != 0;
    }
}
