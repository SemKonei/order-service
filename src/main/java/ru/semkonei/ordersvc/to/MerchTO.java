package ru.semkonei.ordersvc.to;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class MerchTO extends NamedTO {
    private float price;

    public MerchTO() {
    }

    @ConstructorProperties({"id", "name", "price"})
    public MerchTO(Integer id, String name, float price) {
        super(id, name);
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchTO mealTo = (MerchTO) o;
        return  Objects.equals(id, mealTo.id) &&
                Objects.equals(name, mealTo.name) &&
                Objects.equals(price, mealTo.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", name=" + name +
                ", price='" + price + '\'' +
                '}';
    }
}
