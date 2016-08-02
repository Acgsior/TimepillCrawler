package com.acgsior.tdd;

import com.acgsior.factory.URLFactory;
import com.acgsior.model.Diary;
import com.acgsior.selector.impl.DateObjectSelector;
import com.acgsior.selector.impl.LastSplitAttributeSelector;
import com.acgsior.selector.impl.TextObjectSelector;
import com.acgsior.selector.impl.TimeObjectSelector;
import com.acgsior.selector.impl.diary.DiaryCommentCountSelector;
import com.acgsior.selector.impl.diary.DiaryObjectSelector;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class DiarySelectTest {

    private String notebookId = "95005";
    private String diaryDate = "2010-08-04";

    @Resource(name = "tpURLFactory")
    private URLFactory tpURLFactory;

    @Resource(name = "diaryDateSelector")
    private DateObjectSelector diaryDateSelector;

    @Resource(name = "diaryTimeSelector")
    private TimeObjectSelector diaryTimeSelector;

    @Resource(name = "diaryContentSelector")
    private TextObjectSelector diaryContentSelector;

    @Resource(name = "diaryCommentCountSelector")
    private DiaryCommentCountSelector diaryCommentCountSelector;

    @Resource(name = "diaryIdSelector")
    private LastSplitAttributeSelector diaryIdSelector;

    @Resource(name = "diarySelector")
    private DiaryObjectSelector diarySelector;

    @Test
    public void diarySelectTest() throws IOException {
        String dateNotebookURL = tpURLFactory.getURL(URLFactory.DATE_NOTEBOOK, notebookId, diaryDate).get();
        Optional optionalPid = Optional.of(notebookId);
        Assert.assertEquals("http://timepill.net/notebook/95005/2010-08-04", dateNotebookURL);

        Document document = Jsoup.connect(dateNotebookURL).get();

        LocalDate diaryDate = diaryDateSelector.select(document, optionalPid);
        Assert.assertEquals(LocalDate.of(2010, 8, 4), diaryDate);

        Element element = document.select("#diarys .diary").last();

        String diaryId = diaryIdSelector.select(element, optionalPid);
        Assert.assertEquals("296135", diaryId);

        LocalTime diaryTime = diaryTimeSelector.select(element, optionalPid);
        Assert.assertEquals(LocalTime.of(15, 32), diaryTime);

        String content = diaryContentSelector.select(element, optionalPid);
        Assert.assertTrue(StringUtils.isNoneBlank(content));

        String commentCount = diaryCommentCountSelector.select(element, optionalPid);
        Assert.assertEquals("2", commentCount);

        List<Diary> dairies = diarySelector.select(document, optionalPid);
        Assert.assertTrue(dairies.size() > 0);

        Diary diary = Iterables.getLast(dairies);
        Assert.assertEquals("296135", diary.getId());
        Assert.assertTrue(StringUtils.isNoneBlank(diary.getContent()));
        Assert.assertEquals(LocalTime.of(15, 32), diary.getDiaryTime());
        Assert.assertEquals(LocalDate.of(2010, 8, 4), diary.getDiaryDate());
        Assert.assertEquals(2, diary.getCommentCount());
    }
}
