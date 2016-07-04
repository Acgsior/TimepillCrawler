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
		return element.select(getPattern()).get(getElementIndex()).attr(attributePattern);
	}

	public void setAttributePattern(final String attributePattern) {
		this.attributePattern = attributePattern;
	}
}
