package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import ru.semkonei.ordersvc.model.OrderStatus;

import java.time.LocalDateTime;

@Getter
public class OrderRequestTO {

    private LocalDateTime orderDate;

    private OrderStatus status;
}
