package com.acgsior.bootstrap;

/**
 * Created by mqin on 8/4/16.
 */
public interface ICrawledDataCacheLogger {

    void logCacheStatus();

    void logCachedPerson();

    void logCachedNotebooks();

    void logCachedDiaries();
}
