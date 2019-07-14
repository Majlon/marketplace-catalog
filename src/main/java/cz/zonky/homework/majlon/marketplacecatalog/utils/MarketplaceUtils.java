package cz.zonky.homework.majlon.marketplacecatalog.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        PARAM_DATE.setTimeZone(TimeZone.getTimeZone("CET"));
        return PARAM_DATE.format(date);
    }

    /**
     * Method for creating date instance with added amount of time.
     * Amount can be positive or negative.
     *
     * @param timeUnit - Constant from Calendar class
     * @param amountToAdd - amount of time to be added or subtracted
     * @return Date class
     */
    public static Date getComposedDate(int timeUnit, int amountToAdd) {
        Calendar now = Calendar.getInstance();
        now.add(timeUnit, amountToAdd);
        return now.getTime();
    }
}
