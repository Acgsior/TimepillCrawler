package com.acgsior.selector.impl.diary;

import com.acgsior.factory.BeanFactory;
import com.acgsior.model.Diary;
import com.acgsior.selector.ICachedSelector;
import com.acgsior.selector.ObjectSelector;
import com.acgsior.selector.impl.DateObjectSelector;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by mqin on 7/4/16.
 */
public class DiaryObjectSelector extends ObjectSelector implements ICachedSelector {

    @Resource(name = "diaryDateSelector")
    private DateObjectSelector diaryDateSelector;

    @Override
    public List<Diary> select(Document document, Optional parentId) {
        Elements elements = document.select(getPattern());
        List<Diary> dairies = new ArrayList<>();

        LocalDate diaryDate = diaryDateSelector.select(document, Optional.empty());

        elements.forEach(element -> {
            Diary diary = Diary.newInstance();
            Arrays.stream(getSyncSelectors()).forEach(selector -> {
                Object value = selector.select(element, Optional.of(diary.getUid()));
                BeanFactory.setPropertyValueSafely(diary, selector.getProperty(), value);
            });
            diary.setDiaryDate(diaryDate);
            dairies.add(diary);
        });
        return dairies;
    }

    @Override
    public void cache(Object value) {

    }

    public void setDiaryDateSelector(DateObjectSelector diaryDateSelector) {
        this.diaryDateSelector = diaryDateSelector;
    }
}
