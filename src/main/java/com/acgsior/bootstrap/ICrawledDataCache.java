package com.acgsior.bootstrap;

import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.model.Person;

/**
 * Created by mqin on 8/4/16.
 */
public interface ICrawledDataCache {

    void cachePerson(Person person);

    void cacheNotebook(Notebook notebook);

    void cacheDiary(Diary diary);

    int getCachedNotebookCount();

    int getCacheDiaryCount();
}
