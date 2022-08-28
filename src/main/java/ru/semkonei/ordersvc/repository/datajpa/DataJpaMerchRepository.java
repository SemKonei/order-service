package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Merch save(Merch merch) {
        return merchRepository.save(merch);
    }

    @Override
    public Merch get(Integer id) {
        return merchRepository.findById(id).orElse(null);
    }

    @Override
    public List<Merch> getAllById(List<Integer> idList) {
        return merchRepository.findAllById(idList);
    }

    @Override
    public List<Merch> getAll() {
        return merchRepository.findAll(SORTED_NAME);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        Integer result = merchRepository.delete(id);
        return  result != 0 ? result : null;
    }
}
