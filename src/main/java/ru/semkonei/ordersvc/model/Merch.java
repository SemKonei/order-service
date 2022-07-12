package ru.semkonei.ordersvc.model;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
public class Merch extends NamedEntity {

    private Float currentPrice;

    public Merch() {
    }

    public Merch(Merch merch) {
        this(merch.id, merch.name, merch.currentPrice);
    }

    public Merch(Integer id, String name) {
        super(id, name);
    }

    public Merch(Integer id, String name, Float currentPrice) {
        super(id, name);
        this.currentPrice = currentPrice;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", currentPrice=").append(currentPrice)
                .append("}");
        return sb.toString();
    }
}
