package com.acgsior.selector.impl.diary;

import com.acgsior.provider.ApplicationContextProvider;
import com.acgsior.provider.DocumentProvider;
import com.acgsior.provider.ExecutorProvider;
import com.acgsior.selector.impl.LinkAttributeSelector;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by mqin on 7/8/16.
 */
public class DiaryObjectSelectorWrapper implements Runnable {

    @Resource(name = "diarySelector")
    private DiaryObjectSelector diarySelector;

    @Resource(name = "diaryLinkNextMonthSelector")
    private LinkAttributeSelector nextMonthLinkSelector;

    private String location;

    private String parentId;

    private boolean nextMonth;


    public DiaryObjectSelectorWrapper(String location, String parentId, boolean nextMonth) {
        this.location = location;
        this.parentId = parentId;
        this.nextMonth = nextMonth;
    }

    @Override
    public void run() {
        Optional<Document> document = DocumentProvider.fetch(location);
        if (nextMonth) {
            String nextMonthLocation = nextMonthLinkSelector.select(document.get(), Optional.of(parentId));
            if (StringUtils.isNoneBlank(nextMonthLocation)) {
                ExecutorProvider.getDiaryExecutor().submit(new DiaryObjectSelectorWrapper(nextMonthLocation, parentId, true));
            }
        }
    }

    protected DiaryObjectSelector generateDiarySelector() {
        return (DiaryObjectSelector) ApplicationContextProvider.getApplicationContext().getBean("diarySelector");
    }

    public DiaryObjectSelector getDiarySelector() {
        return diarySelector;
    }

    public void setDiarySelector(DiaryObjectSelector diarySelector) {
        this.diarySelector = diarySelector;
    }
}