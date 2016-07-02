package com.acgsior.selector.impl;

import com.acgsior.selector.Selector;
import com.google.common.base.CharMatcher;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class DateSelector extends Selector {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.getDefault());

	@Override
	public LocalDate select(final Document document, final Optional parentId) {
		String dateText = document.select(getPattern()).get(getElementIndex()).text();
		// all date with pattern yyyy-MM-dd, so only reserve numbers to convert to date
		String date = CharMatcher.DIGIT.retainFrom(dateText);
		return LocalDate.parse(date, FORMATTER);
	}
}
