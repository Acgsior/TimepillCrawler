package com.acgsior.cache;

import com.acgsior.bootstrap.ICrawledDataCacheLogger;
import com.acgsior.bootstrap.ILocalDateTimeToStringConverter;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.model.Person;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeMap;

/**
 * Created by Yove on 8/4/16.
 */
public class DatetimeBasedCache implements ICrawledDataCacheLogger, ILocalDateTimeToStringConverter {

	private static Logger logger = LoggerFactory.getLogger(NotebookBasedCache.class);

	private Person personCache;

	/**
	 * <date string, notebook>
	 */
	private TreeMap<String, Notebook> notebookCache = Maps.newTreeMap();
	/**
	 * <datetime string, diary>
	 */
	private TreeMap<String, Diary> diaryCache = Maps.newTreeMap();

	@Override
	public void cachePerson(Person person) {
		personCache = person;
	}

	@Override
	public void logCacheStatus() {
		logger.info("Cached Count of Notebook: " + getCachedNotebookCount());
		logger.info("Cached Count of Diary: " + getCacheDiaryCount());
	}

	@Override
	public void logCachedPerson() {
		logger.info("Cached Person: " + personCache);
	}

	@Override
	public void cacheNotebook(Notebook notebook) {
		String key = localDateToString(notebook.getBegin());
		notebookCache.put(key, notebook);
	}

	@Override
	public void logCachedNotebooks() {
		notebookCache.values().forEach(notebook -> logger.info(notebook.toString()));
	}

	@Override
	public void cacheDiary(Diary diary) {
		String key = localDateTimeToString(diary.getDiaryDate(), diary.getDiaryTime());
		diaryCache.put(key, diary);
	}

	@Override
	public void logCachedDiaries() {
		diaryCache.values().forEach(diary -> logger.info(diary.toString()));
	}

	@Override
	public int getCachedNotebookCount() {
		return notebookCache.size();
	}

	@Override
	public int getCacheDiaryCount() {
		return diaryCache.size();
	}

	public Person getPersonCache() {
		return personCache;
	}

	public TreeMap<String, Notebook> getNotebookCache() {
		return notebookCache;
	}

	public TreeMap<String, Diary> getDiaryCache() {
		return diaryCache;
	}
}
