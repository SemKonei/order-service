package ru.semkonei.ordersvc.to;

public class NamedTO extends BaseTO {

    protected String name;

    public NamedTO() {
    }

    public NamedTO(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String name) {
        this.name = name;
    }
}
