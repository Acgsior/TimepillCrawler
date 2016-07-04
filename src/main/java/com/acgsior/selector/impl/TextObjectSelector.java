package com.acgsior.selector.impl;

import com.acgsior.selector.PropertySelector;
import org.jsoup.nodes.Element;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class TextObjectSelector extends PropertySelector {

	@Override
	public String select(final Element element, final Optional parentId) {
		return element.select(getPattern()).get(getElementIndex()).text();
	}
}
