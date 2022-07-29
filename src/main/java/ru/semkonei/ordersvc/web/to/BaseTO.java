package ru.semkonei.ordersvc.web.to;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" {id=").append(id);
        return sb.toString();
    }
}
