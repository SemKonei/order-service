package ru.semkonei.ordersvc.web.to;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class MerchRequestTO {

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    private float currentPrice;
}
