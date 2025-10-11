package com.notification.api.utils;

import org.springframework.util.ObjectUtils;

import java.util.Calendar;

public final class CommonUtils {

    private static final Calendar calendar = Calendar.getInstance();

    public static long getCurrentTimeStamp() {
        return calendar.getTimeInMillis();
    }

    public static boolean isNotEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static boolean isEmpty(final Object input) {
        return ObjectUtils.isEmpty(input);
    }

}
