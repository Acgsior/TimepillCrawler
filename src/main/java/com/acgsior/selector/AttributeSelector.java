package com.acgsior.selector;

import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class AttributeSelector extends Selector {

	private String attributePattern;

	@Override
	public String select(final Document document, final Optional parentId) {
		return document.body().select(getPattern()).get(getElementIndex()).attr(attributePattern);
	}

	public void setAttributePattern(final String attributePattern) {
		this.attributePattern = attributePattern;
	}
}
