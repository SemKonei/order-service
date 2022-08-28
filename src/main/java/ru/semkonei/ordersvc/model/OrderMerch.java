package ru.semkonei.ordersvc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Entity
@Table(name = "order_merch")
@Getter
@Setter
@NoArgsConstructor
public class OrderMerch extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merch_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty("merchId")
    private Merch merch;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Order order;

    @PositiveOrZero
    @Column(name = "price", nullable = false)
    private Float price;

    @Positive
    @Column(name = "count", nullable = false)
    private int count;

    public OrderMerch(OrderMerch orderMerch) {
        this(orderMerch.id, orderMerch.order, orderMerch.merch, orderMerch.price, orderMerch.count);
    }

    public OrderMerch(Integer id, Order order, Merch merch, Float price, int count) {
        super(id);
        this.order = order;
        this.merch = merch;
        this.price = price;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(" count=").append(count);
        sb.append(" price=").append(price).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderMerch that = (OrderMerch) o;
        return count == that.count &&
                (price != null && price.equals(that.price));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price, count);
    }
}
