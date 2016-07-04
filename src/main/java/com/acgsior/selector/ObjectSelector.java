package com.acgsior.selector;

import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public abstract class ObjectSelector <T> extends AbstractSelector {

	private PropertySelector[] syncSelectors;

	private ObjectSelector[] asyncSelectors;

	public abstract T select(Document document, Optional<String> parentId);

	public PropertySelector[] getSyncSelectors() {
		return syncSelectors;
	}

	public void setSyncSelectors(final PropertySelector[] syncSelectors) {
		this.syncSelectors = syncSelectors;
	}

	public ObjectSelector[] getAsyncSelectors() {
		return asyncSelectors;
	}

	public void setAsyncSelectors(final ObjectSelector[] asyncSelectors) {
		this.asyncSelectors = asyncSelectors;
	}
}