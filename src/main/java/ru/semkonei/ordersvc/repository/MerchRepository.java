package ru.semkonei.ordersvc.repository;

import org.springframework.data.repository.query.Param;
import ru.semkonei.ordersvc.model.Merch;

import java.util.List;

public interface MerchRepository {

    Merch save(Merch merch);

    Merch get(Integer id);

    List<Merch> getAllById(List<Integer> idList);

    List<Merch> getAll();

    Integer delete(Integer id);
}
