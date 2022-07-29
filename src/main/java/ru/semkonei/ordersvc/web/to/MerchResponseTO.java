package ru.semkonei.ordersvc.web.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.semkonei.ordersvc.model.Merch;

@Getter
@Setter
@AllArgsConstructor
public class MerchResponseTO extends NamedTO{

    private float currentPrice;

    public MerchResponseTO(Merch merch) {
        super(merch.getId(), merch.getName());
        this.currentPrice = merch.getCurrentPrice();
    }
}
