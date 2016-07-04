package com.acgsior.model;

import java.time.LocalDate;

/**
 * Created by Yove on 16/07/03.
 */
public class Notebook extends Base {

	private String name;
	private String cover;

	private LocalDate begin;
	private LocalDate end;

	public static Notebook newInstance() {
		return new Notebook();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(final String cover) {
		this.cover = cover;
	}

	public LocalDate getBegin() {
		return begin;
	}

	public void setBegin(final LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(final LocalDate end) {
		this.end = end;
	}
}
