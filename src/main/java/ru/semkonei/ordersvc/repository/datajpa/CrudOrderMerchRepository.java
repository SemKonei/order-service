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
//    @Query(value = "DELETE FROM OrderMerch om WHERE om.id=:id AND om.order.user.id=:userId") не работает, но может когда-нибудь что-то изменится
    @Query(value = "DELETE FROM order_merch " +
            "WHERE order_merch.id=:id " +
            "AND order_merch.order_id=:orderId " +
            "AND :orderId IN" +
                    " (SELECT o.id FROM orders o" +
                    " WHERE o.user_id=:userId)",
            nativeQuery = true)
    int delete(@Param("id") int id, @Param("orderId") int orderId, @Param("userId") int userId);

    @Query("SELECT o FROM OrderMerch o  WHERE o.order.id=:orderId AND o.order.user.id=:userId")
    List<OrderMerch> getAll(@Param("orderId") int orderId, @Param("userId") int userId);

}
