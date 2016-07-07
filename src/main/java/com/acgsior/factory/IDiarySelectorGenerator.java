package com.acgsior.factory;

import com.acgsior.provider.ApplicationContextProvider;
import com.acgsior.selector.impl.diary.DiaryObjectSelector;

/**
 * Created by mqin on 7/7/16.
 */
public interface IDiarySelectorGenerator {

    default DiaryObjectSelector generateDiarySelector() {
        return (DiaryObjectSelector) ApplicationContextProvider.getApplicationContext().getBean("diarySelector");
    }
}
