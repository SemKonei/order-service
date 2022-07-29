package ru.semkonei.ordersvc.testdata;

import ru.semkonei.ordersvc.MatcherFactory;
import ru.semkonei.ordersvc.model.Merch;

import java.util.Arrays;
import java.util.List;

import static ru.semkonei.ordersvc.model.BaseEntity.START_SEQ;

public class MerchTestData {
    public static final MatcherFactory.Matcher<Merch> MERCH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Merch.class);

    public static final int MERCH1_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Merch merch1 = new Merch(MERCH1_ID, "Merch 1", 100f);
    public static final Merch merch2 = new Merch(MERCH1_ID+1, "Merch 2",500f);

    public static List<Merch> merchList = Arrays.asList(merch1, merch2);

    public static Merch getNew() {
        return new Merch(null, "test", 1f);
    }
    public static Merch getUpdated() {
        Merch updated = new Merch(merch1);
        updated.setName("updated");
        updated.setCurrentPrice(2000f);
        return updated;
    }
}
