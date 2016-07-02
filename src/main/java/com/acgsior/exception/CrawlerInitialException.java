package com.acgsior.exception;

/**
 * Created by Yove on 16/07/02.
 */
public class CrawlerInitialException extends RuntimeException {

	public CrawlerInitialException() {
		super();
	}

	public CrawlerInitialException(String message) {
		super(message);
	}

	public CrawlerInitialException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrawlerInitialException(Throwable cause) {
		super(cause);
	}
}
