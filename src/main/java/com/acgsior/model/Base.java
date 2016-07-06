package com.acgsior.model;

/**
 * Created by Yove on 16/07/03.
 */
public abstract class Base {

	private String id;

	private Base parent;

	protected Base() {

	}

	protected Base(String tid) {
		this.id = tid;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Base getParent() {
		return parent;
	}

	public void setParent(final Base parent) {
		this.parent = parent;
	}
}
