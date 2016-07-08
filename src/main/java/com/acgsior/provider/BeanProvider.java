package com.acgsior.provider;

import com.acgsior.selector.impl.diary.DiaryObjectSelectorWrapper;

/**
 * Created by mqin on 7/8/16.
 */
public class BeanProvider {

    public static DiaryObjectSelectorWrapper getDiarySelectorWrapper() {
        return (DiaryObjectSelectorWrapper) ApplicationContextProvider.getApplicationContext().getBean("diarySelectorWrapper");
    }
}
