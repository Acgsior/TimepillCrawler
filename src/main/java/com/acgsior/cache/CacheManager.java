package com.acgsior.cache;

import com.acgsior.bootstrap.ICrawledDataCache;
import com.acgsior.exception.CrawlerInitialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Yove on 8/3/16.
 */
public class CacheManager {

    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private CacheType cacheType;

    private NotebookBasedCache notebookBasedCache;

    private DatetimeBasedCache datetimeBasedCache;

    public ICrawledDataCache getCache() {
        switch (cacheType) {
            case NOTEBOOK_BASED:
                return notebookBasedCache;
            case DATETIME_BASED:
                return datetimeBasedCache;
        }
        throw new CrawlerInitialException("No matched data cache applied.");
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }

    public NotebookBasedCache getNotebookBasedCache() {
        return notebookBasedCache;
    }

    public void setNotebookBasedCache(NotebookBasedCache notebookBasedCache) {
        this.notebookBasedCache = notebookBasedCache;
    }

    public DatetimeBasedCache getDatetimeBasedCache() {
        return datetimeBasedCache;
    }

    public void setDatetimeBasedCache(DatetimeBasedCache datetimeBasedCache) {
        this.datetimeBasedCache = datetimeBasedCache;
    }

    public enum CacheType {
        NOTEBOOK_BASED, DATETIME_BASED
    }
}
