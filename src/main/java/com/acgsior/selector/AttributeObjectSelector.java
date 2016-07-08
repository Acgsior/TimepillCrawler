package com.acgsior.selector;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class AttributeObjectSelector extends PropertySelector {

    protected static final String DEFAULT_VALUE = StringUtils.EMPTY;

    private String attributePattern;

    @Override
    public String select(final Element element, final Optional parentId) {
        Elements elements = element.select(getPattern());
        String value = DEFAULT_VALUE;
        if (!elements.isEmpty()) {
            value = elements.get(getElementIndex()).attr(attributePattern);
        }
        return postHandle(value);
    }

    /**
     * For override
     *
     * @param value
     * @return
     */
    protected String postHandle(String value) {
        return value;
    }

    public void setAttributePattern(final String attributePattern) {
        this.attributePattern = attributePattern;
    }
}
