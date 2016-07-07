package com.acgsior.factory;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
public class URLFactory {

	public static final String PERSON = "person";

	public static final String NOTEBOOK = "notebook";
	public static final String DATE_NOTEBOOK = "date_notebook";

	public static final String DIARY = "diary";

	private Map<String, String> URLMap;

	public URLFactory(final Map<String, String> URLMap) {
		this.URLMap = URLMap;
	}

	public Optional<String> getURL(String URLKey, Object... parameters) {
		return URLMap.containsKey(URLKey) ? Optional.of(MessageFormat.format(URLMap.get(URLKey), parameters)) : Optional.empty();
	}
}
