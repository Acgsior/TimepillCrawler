package com.acgsior.exception;

/**
 * Created by Yove on 16/07/02.
 */
public class CrawlerRuntimeException extends RuntimeException {

	public CrawlerRuntimeException() {
		super();
	}

	public CrawlerRuntimeException(String message) {
		super(message);
	}

	public CrawlerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrawlerRuntimeException(Throwable cause) {
		super(cause);
	}
}
