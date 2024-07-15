package com.example.productservice.util;

import com.example.commonservice.common.TimePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class TimeUtils {
    public static final String PATTERN_LONG_DATETIME = "yyyyMMddHHmmss";
    public static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public TimeUtils() {
    }

    public static String timeToString(LocalDateTime dateTime, TimePattern pattern) {
        return dateTime != null ? pattern.getFormatter().format(dateTime) : null;
    }

    public static LocalDateTime stringToTime(String timeStr, TimePattern pattern) {
        return timeStr != null ? LocalDateTime.parse(timeStr, pattern.getFormatter()) : null;
    }

    public static LocalDateTime localDateToLocalDateTime(Object timeObj, TimePattern pattern) {
        return Objects.isNull(timeObj) ? null : LocalDate.parse(timeObj.toString(), pattern.getFormatter()).atStartOfDay();
    }

    public static String stringToZonedDateTimeString(String timeStr) {
        if (StringUtils.hasText(timeStr)) {
            LocalDateTime localDateTime = stringToTime(timeStr, TimePattern.YYYY_MM_DD_HH_MM_SS);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            return TimePattern.YYYY_MM_DD_T_HH_MM_SS_SSSZ.getFormatter().format(zonedDateTime);
        } else {
            return null;
        }
    }

    public static LocalDateTime stringLocalDateToLocalDateTime(String timeStr, TimePattern pattern) {
        if (StringUtils.hasText(timeStr)) {
            LocalDate localDate = LocalDate.parse(timeStr, pattern.getFormatter());
            return localDate.atStartOfDay();
        } else {
            return null;
        }
    }

    public static String toLongDate(LocalDate date) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            return dtf.format(date);
        } catch (Exception var2) {
            return "";
        }
    }

    public static String toLongDate(LocalDateTime dateTime) {
        return toLongDate(dateTime.toLocalDate());
    }

    public static Date toDate(Long longDate) {
        try {
            if (Objects.isNull(longDate)) {
                logger.warn("Null input");
                return new Date();
            } else {
                return (new SimpleDateFormat("yyyyMMdd")).parse(longDate.toString());
            }
        } catch (ParseException var2) {
            logger.error("Error parse to java.util.Date with input {}", longDate);
            return new Date();
        }
    }

    public static LocalDate toLocalDate(Long longDate) {
        try {
            if (Objects.isNull(longDate)) {
                logger.warn("Null input");
                return null;
            } else {
                return LocalDate.parse(String.valueOf(longDate), DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
        } catch (Exception var2) {
            logger.error("Error parse to LocalDate with input {}", longDate);
            return null;
        }
    }

    public static LocalDateTime toLocalDateTime(Long longDate) {
        try {
            if (Objects.isNull(longDate)) {
                logger.warn("Null input");
                return null;
            } else {
                return LocalDateTime.parse(String.valueOf(longDate), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            }
        } catch (Exception var2) {
            logger.error("Error parse to LocalDateTime with input {}", longDate);
            return null;
        }
    }

    public static Long toLongDate2(LocalDateTime date) {
        try {
            return Long.parseLong(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        } catch (Exception var2) {
            return null;
        }
    }

    public static long getDayBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static long getDayBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static LocalDateTime trunc(LocalDateTime date) {
        return date != null ? date.withHour(0).withMinute(0).withSecond(0).withNano(0) : null;
    }

    public static long checkForFeb29(LocalDateTime start, LocalDateTime end) {
        long numberDayLeaps = 0L;
        start = start.with(LocalTime.MIN);
        end = end.with(LocalTime.MIN);
        int yearStart = start.getYear();
        int yearEnd = end.getYear();

        for (int a = yearStart; a <= yearEnd; ++a) {
            if (a % 4 == 0 && a % 100 != 0 || a % 400 == 0) {
                LocalDateTime dateTime = LocalDateTime.of(a, Month.FEBRUARY, 29, 0, 0);
                if (!dateTime.isBefore(start) && !dateTime.isAfter(end)) {
                    ++numberDayLeaps;
                }
            }
        }

        return numberDayLeaps;
    }

    public static long getDayBetweenCheckFeb29(LocalDateTime startDate, LocalDateTime endDate, Boolean isHour) {
        Duration duration = Duration.between(startDate, endDate);
        long days = duration.toDays();
        if (isHour != null) {
            int time = isHour ? 24 : 1440;
            long timeUnit = isHour ? duration.toHours() : duration.toMinutes();
            if (timeUnit % (long) time >= 1L) {
                ++days;
            }
        }
        days -= checkForFeb29(startDate, endDate);
        return days;
    }
}

