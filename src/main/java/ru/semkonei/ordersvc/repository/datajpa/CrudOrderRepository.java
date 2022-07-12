package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.Order;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudOrderRepository extends JpaRepository<Order, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Order o WHERE o.id=:id AND o.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT o FROM Order o WHERE o.user.id=:userId")
    List<Order> getAll(@Param("userId") int userId);
}
