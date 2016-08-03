package com.acgsior.selector.impl;

import com.acgsior.selector.PropertySelector;
import org.jsoup.nodes.Element;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by mqin on 7/4/16.
 */
public class TimeObjectSelector extends PropertySelector<LocalTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h : m a", Locale.getDefault());

    @Override
    public LocalTime select(final Element element, final Optional<String> parentId) {
        String dateText = element.select(getPattern()).get(getElementIndex()).text().toUpperCase();
        // all date with pattern  3 : 32 pm, so only reserve numbers to convert to date
        return LocalTime.parse(dateText, FORMATTER);
    }
}
