package ru.semkonei.ordersvc.service;

import ru.semkonei.ordersvc.model.OrderMerch;
import ru.semkonei.ordersvc.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static ru.semkonei.ordersvc.testdata.MerchTestData.MERCH1_ID;
import static ru.semkonei.ordersvc.testdata.OrderMerchTestData.*;
import static ru.semkonei.ordersvc.testdata.OrderTestData.ORDER1_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class OrderMerchServiceTest extends AbstractServiceTest {

    @Autowired
    private OrderMerchService service;

    @Test
    public void save() {
        OrderMerch newOM = getNew();
        OrderMerch created = service.create(getNew());
        newOM.setId(created.id());
        ORDERMERCH_MATCHER.assertMatch(created, newOM);
        ORDERMERCH_MATCHER.assertMatch(service.get(ORDERMERCH1_ID+2), newOM);
    }

    @Test
    public void get() {
        ORDERMERCH_MATCHER.assertMatch(service.get(ORDERMERCH1_ID), ORDER_DATA_1);
    }

    @Test
    public void getAll() {
        ORDERMERCH_MATCHER.assertMatch(service.getAll(ORDER1_ID), orderMerchList);
    }

    @Test
    public void getNotFoundMerch() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }


    @Test
    public void update() {
        OrderMerch updated = getUpdated();
        service.update(updated, ORDER1_ID, MERCH1_ID);
        ORDERMERCH_MATCHER.assertMatch(service.get(ORDERMERCH1_ID), getUpdated());
    }

    @Test
    public void delete() {
        service.delete(ORDERMERCH1_ID);
        assertThrows(NotFoundException.class, () -> service.get(ORDERMERCH1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThat(service.delete(NOT_FOUND)).isEqualTo(false);
    }
}
