package ru.semkonei.ordersvc.web.to;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NamedTO extends BaseTO {

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    public NamedTO() {
    }

    public NamedTO(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append(", name=").append(name);
        return sb.toString();
    }
}
