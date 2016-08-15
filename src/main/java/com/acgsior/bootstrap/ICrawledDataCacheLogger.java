package com.acgsior.bootstrap;

/**
 * Created by Yove on 8/4/16.
 */
public interface ICrawledDataCacheLogger extends ICrawledDataCache {

    void logCacheStatus();

    void logCachedPerson();

    void logCachedNotebooks();

    void logCachedDiaries();
}
