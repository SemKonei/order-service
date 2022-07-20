package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.to.MerchTO;

import java.util.List;
import java.util.stream.Collectors;

public class MerchUtil {
    public static List<MerchTO> getTos(List<Merch> merches) {
        return merches.stream().map(MerchUtil::createTo).collect(Collectors.toList());
    }

    public static MerchTO createTo(Merch merch) {
        return new MerchTO(merch.getId(), merch.getName(), merch.getCurrentPrice());
    }
    public static Merch getFromTo(MerchTO merchTO) {
        return new Merch(merchTO.getId(), merchTO.getName(), merchTO.getPrice());
    }
}
