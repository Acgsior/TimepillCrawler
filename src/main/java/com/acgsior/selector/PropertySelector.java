package com.acgsior.selector;

import org.jsoup.nodes.Element;

import java.util.Optional;

/**
 * Created by Yove on 16/07/03.
 */
public abstract class PropertySelector <T> extends AbstractSelector {

	public abstract T select(Element element, Optional<String> parentId);
}
