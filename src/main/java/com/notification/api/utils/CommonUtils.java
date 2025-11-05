package com.notification.api.utils;

import com.notification.api.models.context.NotificationContextHolder;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.notification.api.constants.ApplicationConstants.X_REQUEST_ID;

public final class CommonUtils {

    private static final Calendar calendar = Calendar.getInstance();
    private static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{([A-Za-z0-9_]+)}");

    public static long getCurrentTimeStamp() {
        return calendar.getTimeInMillis();
    }

    public static boolean isNotEmpty(final Object input) {
        return !ObjectUtils.isEmpty(input);
    }

    public static boolean isEmpty(final Object input) {
        return ObjectUtils.isEmpty(input);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getCurrentTenantId() {
        return NotificationContextHolder.getContext().tenantId();
    }

    public static String getCurrentTraceId() {
        return MDC.get(X_REQUEST_ID);
    }

    public static Set<String> extractDynamicVariables(final String template) {
        Set<String> variables = new HashSet<>();
        Matcher matcher = TEMPLATE_PATTERN.matcher(template);
        while (matcher.find()) {
            variables.add(matcher.group(1));
        }
        return variables;
    }

}
