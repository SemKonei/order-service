package ru.semkonei.ordersvc.util.toUtils;

import ru.semkonei.ordersvc.model.Merch;
import ru.semkonei.ordersvc.web.to.MerchRequestTO;
import ru.semkonei.ordersvc.web.to.MerchResponseTO;

import java.util.List;
import java.util.stream.Collectors;

public class MerchUtil {
    public static List<MerchResponseTO> getTos(List<Merch> merches) {
        return merches.stream().map(MerchUtil::createTo).collect(Collectors.toList());
    }

    public static MerchResponseTO createTo(Merch merch) {
        return new MerchResponseTO(merch);
    }

    public static Merch updateFromTo(Merch merch, MerchRequestTO merchTO) {
        merch.setName(merchTO.getName());
        merch.setCurrentPrice(merchTO.getCurrentPrice());
        return merch;
    }

    public static Merch getFromTo(MerchRequestTO merchTO) {
        return new Merch(merchTO.getName(), merchTO.getCurrentPrice());
    }
}
