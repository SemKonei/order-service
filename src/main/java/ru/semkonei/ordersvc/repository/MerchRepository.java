package ru.semkonei.ordersvc.repository;

import ru.semkonei.ordersvc.model.Merch;

import java.util.List;

public interface MerchRepository {

    Merch save(Merch merch);

    Merch get(Integer id);

    List<Merch> getAll();

    boolean delete(Integer id);
}
