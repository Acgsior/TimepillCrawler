package com.acgsior.selector;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * Created by Yove on 16/07/03.
 */
public abstract class AbstractSelector {

    private String id;

    private int elementIndex = 0;

    private String pattern;

    public String getProperty() {
        return Iterables.getLast(Splitter.on('.').splitToList(id));
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public int getElementIndex() {
        return elementIndex;
    }

    public void setElementIndex(final int elementIndex) {
        this.elementIndex = elementIndex;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
