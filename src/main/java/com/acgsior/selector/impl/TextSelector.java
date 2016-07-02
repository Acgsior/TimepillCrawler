package com.acgsior.selector.impl;

import com.acgsior.selector.Selector;
import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class TextSelector extends Selector {

	@Override
	public String select(final Document document, final Optional parentId) {
		return document.select(getPattern()).get(getElementIndex()).text();
	}
}
