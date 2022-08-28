package ru.semkonei.ordersvc.web.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.semkonei.ordersvc.model.HasId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTO implements HasId {

    protected Integer id;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" {id=").append(id);
        return sb.toString();
    }
}
