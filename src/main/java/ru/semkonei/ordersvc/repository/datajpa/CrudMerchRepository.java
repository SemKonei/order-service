package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Merch;

@Transactional(readOnly = true)
public interface CrudMerchRepository extends JpaRepository<Merch, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Merch m WHERE m.id=:id")
    Integer delete(@Param("id") int id);
}
