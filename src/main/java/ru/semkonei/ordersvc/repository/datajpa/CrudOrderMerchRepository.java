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
    @Query("DELETE FROM OrderMerch o WHERE o.id=:id AND o.order.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT o FROM OrderMerch o WHERE o.order.id=:orderId AND o.order.user.id=:userId")
    List<OrderMerch> getAll(@Param("orderId") int orderId, @Param("userId") int userId);

}
