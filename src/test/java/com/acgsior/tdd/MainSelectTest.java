package com.acgsior.tdd;

import com.acgsior.factory.ImagePathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.image.ImageType;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.model.Person;
import com.acgsior.selector.PropertySelector;
import com.acgsior.selector.impl.*;
import com.acgsior.selector.impl.diary.DiaryCommentCountSelector;
import com.acgsior.selector.impl.diary.DiaryObjectSelector;
import com.acgsior.selector.impl.notebook.NotebookObjectSelector;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainSelectTest {

    private String personId = "100079421";
    private String notebookId = "95005";
    private String diaryDate = "2010-08-04";

    @Resource(name = "tpURLFactory")
    private URLFactory tpURLFactory;

    @Autowired
    private ImagePathFactory imagePathFactory;

    @Resource(name = "personNameSelector")
    private PropertySelector<String> personNameSelector;

    @Resource(name = "registerDateSelector")
    private PropertySelector<LocalDate> registerDateSelector;

    @Resource(name = "personDescriptionSelector")
    private PropertySelector<String> personDescriptionSelector;

    @Resource(name = "personSelector")
    private PersonObjectSelector personSelector;

    @Resource(name = "avatarSelector")
    private PropertySelector<String> avatarSelector;

    @Resource(name = "notebookSelector")
    private NotebookObjectSelector notebookSelector;

    @Resource(name = "notebookIdSelector")
    private LastSplitAttributeSelector lastSplitAttributeSelector;

    @Resource(name = "notebookNameSelector")
    private TextObjectSelector notebookNameSelector;

    @Resource(name = "notebookBeginEndDateSelector")
    private PropertySelector<List<LocalDate>> beginEndDateSelector;

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
    public void peopleInfoSelectTest() throws IOException {
        String personURL = tpURLFactory.getURL(URLFactory.PERSON, personId).get();
        Assert.assertEquals("http://timepill.net/people/100079421", personURL);

        Document document = Jsoup.connect(personURL).get();

        String personName = personNameSelector.select(document, Optional.empty());
        Assert.assertEquals("Excelsior†", personName);

        LocalDate registerDate = registerDateSelector.select(document, Optional.empty());
        Assert.assertEquals(LocalDate.of(2012, 4, 5), registerDate);

        String personDescription = personDescriptionSelector.select(document, Optional.empty());
        Assert.assertTrue(StringUtils.isNoneBlank(personDescription));

        String avatarImageName = avatarSelector.select(document, Optional.of("100079421"));
        Assert.assertEquals("100079421", avatarImageName);

        Person person = personSelector.select(document, Optional.of("100079421"));
        Assert.assertEquals("100079421", person.getTid());
        Assert.assertEquals("Excelsior†", person.getName());
        Assert.assertEquals(LocalDate.of(2012, 4, 5), person.getRegisterDate());
    }

    @Test
    public void imagePathFactoryWorkTest() {
        String extension = imagePathFactory.getImageExtension("http//s.timepill.net/user_icon/20015/b100079421.jpg?v=43");
        Assert.assertEquals("jpg", extension);

        String path = imagePathFactory.getPathWithoutExtension(ImageType.IMAGE, "100079421");
        Assert.assertEquals("/Users/Yove/Temp/timepill/image/100079421", path);
    }

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
        Assert.assertEquals("95005", notebook.getTid());
        Assert.assertEquals("- Closed Note -", notebook.getName());
        Assert.assertEquals(LocalDate.of(2010, 7, 29), notebook.getBegin());
        Assert.assertEquals(LocalDate.of(2016, 3, 31), notebook.getEnd());
    }

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
        Assert.assertEquals("296135", diary.getTid());
        Assert.assertTrue(StringUtils.isNoneBlank(diary.getContent()));
        Assert.assertEquals(LocalTime.of(15, 32), diary.getDiaryTime());
        Assert.assertEquals(LocalDate.of(2010, 8, 4), diary.getDiaryDate());
        Assert.assertEquals(2, diary.getCommentCount());
    }
}
