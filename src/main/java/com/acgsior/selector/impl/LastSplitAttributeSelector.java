package com.acgsior.selector.impl;

import com.acgsior.selector.AttributeObjectSelector;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * Created by mqin on 7/4/16.
 */
public class LastSplitAttributeSelector extends AttributeObjectSelector {

    private char split;

    @Override
    protected String postHandle(String value) {
        return Iterables.getLast(Splitter.on(split).split(value));
    }

    public void setSplit(char split) {
        this.split = split;
    }
}
