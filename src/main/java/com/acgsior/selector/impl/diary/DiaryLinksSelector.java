package com.acgsior.selector.impl.diary;

import com.acgsior.bootstrap.IStandardizeURL;
import com.acgsior.provider.DocumentProvider;
import com.acgsior.selector.PropertySelector;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mqin on 8/2/16.
 */
public class DiaryLinksSelector extends PropertySelector<List<String>> implements IStandardizeURL {

    private String nextMonthPattern;

    @Override
    public List<String> select(Element element, Optional parentId) {
        return selectDiaryLinksRecursively(element);
    }

    protected List<String> selectDiaryLinksRecursively(Element element) {
        List<String> diaryLinks = Lists.newArrayList(selectDiaryLinks(element));

        selectNextMonthDiaryLinks(element, diaryLinks);
        return diaryLinks;
    }

    protected void selectDiaryLinksRecursively(String URL, List<String> diaryLinks) {
        Optional<Document> documentOptional = DocumentProvider.fetch(URL);
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();

            diaryLinks.addAll(selectDiaryLinks(document));
            selectNextMonthDiaryLinks(document, diaryLinks);
        }
    }

    protected void selectNextMonthDiaryLinks(Element element, List<String> diaryLinks) {
        Elements nextMonth = element.select(nextMonthPattern);
        if (CollectionUtils.isNotEmpty(nextMonth)) {
            selectDiaryLinksRecursively(standardizeURL(nextMonth.get(0).attr("href")), diaryLinks);
        }
    }

    protected List<String> selectDiaryLinks(Element element) {
        return element.select(getPattern()).stream().map(e -> standardizeURL(e.attr("href"))).collect(Collectors.toList());
    }

    public void setNextMonthPattern(String nextMonthPattern) {
        this.nextMonthPattern = nextMonthPattern;
    }
}
