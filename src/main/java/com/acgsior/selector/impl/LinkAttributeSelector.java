package com.acgsior.selector.impl;

import com.acgsior.selector.AttributeObjectSelector;

/**
 * Created by mqin on 7/7/16.
 */
public class LinkAttributeSelector extends AttributeObjectSelector {

    private boolean standardize = true;

    @Override
    protected String postHandle(String value) {
        return standardize ? standardizeURL(value) : value;
    }

    protected String standardizeURL(String URL) {
        if (!URL.startsWith("http:")) {
            return "http:".concat(URL);
        }
        return URL;
    }

    public void setStandardize(boolean standardize) {
        this.standardize = standardize;
    }
}