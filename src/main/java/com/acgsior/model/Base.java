package com.acgsior.model;

import java.util.UUID;

/**
 * Created by Yove on 16/07/03.
 */
public abstract class Base {

	private String tid;

	private UUID uid;

	private Base parent;

	protected Base() {
		uid = UUID.randomUUID();
	}

	protected Base(String tid) {
		this.tid = tid;
		uid = UUID.randomUUID();
	}

	public String getTid() {
		return tid;
	}

	public void setTid(final String tid) {
		this.tid = tid;
	}

	public UUID getUid() {
		return uid;
	}

	public void setUid(final UUID uid) {
		this.uid = uid;
	}

	public Base getParent() {
		return parent;
	}

	public void setParent(final Base parent) {
		this.parent = parent;
	}
}
