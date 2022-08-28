package ru.semkonei.ordersvc.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Merch")
public class Merch extends NamedEntity {

    @Column(name = "curr_price", nullable = false)
    @PositiveOrZero
    @NotNull
    private Float currentPrice;

    public Merch(Merch merch) {
        this(merch.id, merch.name, merch.currentPrice);
    }

    public Merch(Integer id) {
        super(id, null);
    }

    public Merch(Integer id, String name) {
        super(id, name);
    }

    public Merch(Integer id, String name, Float currentPrice) {
        super(id, name);
        this.currentPrice = currentPrice;
    }

    public Merch(String name, Float currentPrice) {
        super(null, name);
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", currentPrice=").append(currentPrice)
                .append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Merch merch = (Merch) o;
        return currentPrice != null && currentPrice.equals(merch.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currentPrice);
    }
}
