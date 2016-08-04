package com.acgsior.bootstrap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by mqin on 8/4/16.
 */
public interface ILocalDateTimeToStringConvertor {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());

    default String localDateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    default String localDateTimeToString(LocalDate date, LocalTime time) {
        return date.format(DATE_FORMATTER).concat(time.format(TIME_FORMATTER));
    }
}
