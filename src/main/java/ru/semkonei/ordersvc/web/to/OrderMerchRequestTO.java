package ru.semkonei.ordersvc.web.to;

import lombok.Getter;

@Getter
public class OrderMerchRequestTO {

    protected Integer merchId;

    protected Integer orderId;

    protected Float price;

    protected int count;
}
