package com.acgsior.selector.impl.notebook;

import com.acgsior.selector.PropertySelector;
import com.google.common.base.Splitter;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mqin on 7/4/16.
 */
public class NotebookBeginEndDateSelector extends PropertySelector {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());

    @Override
    public List<LocalDate> select(Element element, Optional parentId) {
        String combinedDateText = element.select(getPattern()).get(getElementIndex()).text();
        // all date with pattern yyyy-MM-dd, so only reserve numbers to convert to date
        List<String> dateTexts = Splitter.on(' ').splitToList(combinedDateText);
        return dateTexts.stream()
                .filter(dateText -> dateText.length() == 10)
                .map(dateText -> LocalDate.parse(dateText, FORMATTER))
                .collect(Collectors.toList());
    }
}