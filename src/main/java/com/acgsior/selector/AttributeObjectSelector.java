package com.acgsior.selector;

import org.jsoup.nodes.Element;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class AttributeObjectSelector extends PropertySelector {

    private String attributePattern;

    @Override
    public String select(final Element element, final Optional parentId) {
        String value = element.select(getPattern()).get(getElementIndex()).attr(attributePattern);
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
