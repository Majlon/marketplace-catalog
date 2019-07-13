package cz.zonky.homework.majlon.marketplacecatalog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MarketplaceUtils {

    private static final SimpleDateFormat ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final SimpleDateFormat PARAM_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public static String toIso8601Date(Date date) {
        ISO8601.setTimeZone(TimeZone.getTimeZone("CET"));
        return ISO8601.format(date);
    }

    public static String toParamDate(Date date) {
        return PARAM_DATE.format(date);
    }
}
