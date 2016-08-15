package com.acgsior.provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yove on 7/8/16.
 */
public class ExecutorProvider {

    public static ExecutorService diaryExecutor;

    public static ExecutorService getDiaryExecutor() {
        if (diaryExecutor == null) {
            synchronized (ExecutorProvider.class) {
                if (diaryExecutor == null) {
                    diaryExecutor = Executors.newFixedThreadPool(1000);
                }
            }
        }
        return diaryExecutor;
    }
}
