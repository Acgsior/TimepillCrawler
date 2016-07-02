package com.acgsior.selector;

import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public abstract class Selector <T> {

	private Selector[] syncSelectors;

	private Selector[] asyncSelectors;

	private String id;

	private String parentId;

	private String pattern;

	private int elementIndex = 0;

	public abstract T select(Document document, Optional<String> parentId);

	public Selector[] getSyncSelectors() {
		return syncSelectors;
	}

	public void setSyncSelectors(final Selector[] syncSelectors) {
		this.syncSelectors = syncSelectors;
	}

	public Selector[] getAsyncSelectors() {
		return asyncSelectors;
	}

	public void setAsyncSelectors(final Selector[] asyncSelectors) {
		this.asyncSelectors = asyncSelectors;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(final String parentId) {
		this.parentId = parentId;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	public int getElementIndex() {
		return elementIndex;
	}

	public void setElementIndex(final int elementIndex) {
		this.elementIndex = elementIndex;
	}
}