package ru.semkonei.ordersvc.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.semkonei.ordersvc.model.OrderMerch;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudOrderMerchRepository extends JpaRepository<OrderMerch, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM OrderMerch o WHERE o.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT o FROM OrderMerch o WHERE o.order.id=:orderId")
    List<OrderMerch> getAll(@Param("orderId") int orderId);

    @Query("SELECT o FROM OrderMerch o WHERE o.id=:id")
    OrderMerch get(@Param("id") int id);
}
