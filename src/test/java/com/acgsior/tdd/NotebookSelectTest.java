package com.acgsior.tdd;

import com.acgsior.factory.URLFactory;
import com.acgsior.model.Notebook;
import com.acgsior.selector.PropertySelector;
import com.acgsior.selector.impl.LastSplitAttributeSelector;
import com.acgsior.selector.impl.TextObjectSelector;
import com.acgsior.selector.impl.diary.DiaryLinksSelector;
import com.acgsior.selector.impl.notebook.NotebookObjectSelector;
import com.google.common.collect.Iterables;
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
import java.util.List;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class NotebookSelectTest {

    private String personId = "100079421";
    private String notebookId = "95005";

    @Resource(name = "tpURLFactory")
    private URLFactory tpURLFactory;

    @Resource(name = "notebookSelector")
    private NotebookObjectSelector notebookSelector;

    @Resource(name = "notebookIdSelector")
    private LastSplitAttributeSelector lastSplitAttributeSelector;

    @Resource(name = "notebookNameSelector")
    private TextObjectSelector notebookNameSelector;

    @Resource(name = "notebookBeginEndDateSelector")
    private PropertySelector<List<LocalDate>> beginEndDateSelector;

    @Resource(name = "diaryLinksSelector")
    private DiaryLinksSelector diaryLinksSelector;

    @Test
    public void notebooksSelectTest() throws IOException {
        String personURL = tpURLFactory.getURL(URLFactory.PERSON, personId).get();
        Optional optionalPid = Optional.of(personId);

        Document document = Jsoup.connect(personURL).get();
        Element element = document.select(".notebooks .notebook").last();

        String id = lastSplitAttributeSelector.select(element, optionalPid);
        Assert.assertEquals("95005", id);

        String notebookName = notebookNameSelector.select(element, optionalPid);
        Assert.assertEquals("- Closed Note -", notebookName);

        List<LocalDate> dates = beginEndDateSelector.select(element, optionalPid);
        Assert.assertNotNull(dates);
        Assert.assertEquals(2, dates.size());
        Assert.assertEquals(LocalDate.of(2010, 7, 29), dates.get(0));
        Assert.assertEquals(LocalDate.of(2016, 3, 31), dates.get(1));

        List<Notebook> notebooks = notebookSelector.select(document, optionalPid);
        Assert.assertTrue(notebooks.size() > 0);

        Notebook notebook = Iterables.getLast(notebooks);
        Assert.assertEquals("95005", notebook.getId());
        Assert.assertEquals("- Closed Note -", notebook.getName());
        Assert.assertEquals(LocalDate.of(2010, 7, 29), notebook.getBegin());
        Assert.assertEquals(LocalDate.of(2016, 3, 31), notebook.getEnd());
    }

    @Test
    public void diaryLinksSelectTest() throws IOException {
        String personURL = tpURLFactory.getURL(URLFactory.NOTEBOOK, notebookId).get();
        Optional optionalPid = Optional.of(notebookId);

        Document document = Jsoup.connect(personURL).get();
        List<String> diaryLinks = diaryLinksSelector.select(document, optionalPid);

        Assert.assertEquals(diaryLinks.size(), 608);
    }

}
