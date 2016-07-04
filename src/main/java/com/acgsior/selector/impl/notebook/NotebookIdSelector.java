package com.acgsior.selector.impl.notebook;

import com.acgsior.selector.AttributeObjectSelector;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

/**
 * Created by mqin on 7/4/16.
 */
public class NotebookIdSelector extends AttributeObjectSelector {

    @Override
    protected String postHandle(String value) {
        return Iterables.getLast(Splitter.on('/').split(value));
    }
}
