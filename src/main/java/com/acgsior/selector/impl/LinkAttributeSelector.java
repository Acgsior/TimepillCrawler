package com.acgsior.selector.impl;

import com.acgsior.bootstrap.IStandardizeURL;
import com.acgsior.selector.AttributeObjectSelector;

/**
 * Created by Yove on 7/7/16.
 */
public class LinkAttributeSelector extends AttributeObjectSelector implements IStandardizeURL {

    private boolean standardize = true;

    @Override
    protected String postHandle(String value) {
        return standardize ? standardizeURL(value) : value;
    }

    public void setStandardize(boolean standardize) {
        this.standardize = standardize;
    }
}
