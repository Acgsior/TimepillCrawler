package com.acgsior.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Yove on 7/7/16.
 */
public class DocumentProvider {

	private static Logger log = LoggerFactory.getLogger(DocumentProvider.class);

	public static Optional<Document> fetch(String URL) {
		try {
			return Optional.of(Jsoup.connect(URL).get());
		} catch (IOException e) {
			log.warn(String.format("Failed fetch URL: %s", URL), e);
		}
		return Optional.empty();
	}
}
