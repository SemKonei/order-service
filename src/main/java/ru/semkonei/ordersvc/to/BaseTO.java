package ru.semkonei.ordersvc.to;

import ru.semkonei.ordersvc.model.HasId;

public class BaseTO implements HasId {

    protected Integer id;

    public BaseTO() {
    }

    public BaseTO(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
