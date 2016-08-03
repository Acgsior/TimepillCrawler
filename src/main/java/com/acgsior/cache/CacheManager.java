package com.acgsior.cache;

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
public class CacheManager {

    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private Person personCache;

    private TreeMap<String, Notebook> notebookCache = Maps.newTreeMap();

    private TreeMap<String, TreeMap<String, Diary>> diaryCache = Maps.newTreeMap();

    public void cachePerson(Person person) {
        this.personCache = person;
    }

    public void cacheNotebook(Notebook notebook) {
        notebookCache.put(notebook.getId(), notebook);
        diaryCache.put(notebook.getId(), Maps.newTreeMap());
    }

    public void cacheDiary(Diary diary) {
        diaryCache.get(diary.getParent()).put(diary.getId(), diary);
    }

    public int getCachedNotebookCount() {
        return notebookCache.size();
    }

    public int getCacheDiaryCount() {
        return diaryCache.values().stream().mapToInt(TreeMap::size).sum();
    }

    public void logCacheStatus() {
        logger.info("Cached Person: " + personCache);
        logger.info("Cached Count of Notebook: " + getCachedNotebookCount());
        logger.info("Cached Count of Diary: " + getCacheDiaryCount());
    }
}
