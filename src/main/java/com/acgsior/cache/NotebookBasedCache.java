package com.acgsior.cache;

import com.acgsior.bootstrap.ICrawledDataCache;
import com.acgsior.bootstrap.ICrawledDataCacheLogger;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.model.Person;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeMap;

/**
 * Created by mqin on 8/3/16.
 */
public class NotebookBasedCache implements ICrawledDataCache, ICrawledDataCacheLogger {

    private static Logger logger = LoggerFactory.getLogger(NotebookBasedCache.class);

    private Person personCache;

    /**
     * <notebook id, notebook>
     */
    private TreeMap<String, Notebook> notebookCache = Maps.newTreeMap();
    /**
     * <notebook id, <diary id, diary>>
     */
    private TreeMap<String, TreeMap<String, Diary>> notebookDiaryCache = Maps.newTreeMap();

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
        notebookCache.put(notebook.getId(), notebook);
        notebookDiaryCache.put(notebook.getId(), Maps.newTreeMap());
    }

    @Override
    public void logCachedNotebooks() {
        notebookCache.values().forEach(notebook -> logger.info(notebook.toString()));
    }

    @Override
    public void cacheDiary(Diary diary) {
        notebookDiaryCache.get(diary.getParent()).put(diary.getId(), diary);
    }

    @Override
    public void logCachedDiaries() {
        notebookDiaryCache.values().forEach(notebookDiaryCache -> notebookDiaryCache.values().forEach(diary -> logger.info(diary.toString())));
    }

    @Override
    public int getCachedNotebookCount() {
        return notebookCache.size();
    }

    @Override
    public int getCacheDiaryCount() {
        return notebookDiaryCache.values().stream().mapToInt(TreeMap::size).sum();
    }
}
