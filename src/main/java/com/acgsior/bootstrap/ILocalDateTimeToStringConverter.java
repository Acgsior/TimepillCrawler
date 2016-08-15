package com.acgsior.bootstrap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Yove on 8/4/16.
 */
public interface ILocalDateTimeToStringConverter {

	DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
	DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());

	default String localDateToString(LocalDate date) {
		return localDateToString(date, DATE_FORMATTER);
	}

	default String localDateToString(LocalDate date, DateTimeFormatter formatter) {
		return date.format(formatter);
	}

	default String localTimeToString(LocalTime time) {
		return localTimeToString(time, TIME_FORMATTER);
	}

	default String localTimeToString(LocalTime time, DateTimeFormatter formatter) {
		return time.format(formatter);
	}

	default String localDateTimeToString(LocalDate date, LocalTime time) {
		return localDateToString(date).concat(localTimeToString(time));
	}
}
