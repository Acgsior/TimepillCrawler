package com.acgsior.tdd;

import com.acgsior.factory.ImagePathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.image.ImageType;
import com.acgsior.model.Notebook;
import com.acgsior.model.Person;
import com.acgsior.selector.PropertySelector;
import com.acgsior.selector.impl.NotebookObjectSelector;
import com.acgsior.selector.impl.PersonObjectSelector;
import com.acgsior.selector.impl.TextObjectSelector;
import com.acgsior.selector.impl.notebook.NotebookIdSelector;
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
import java.util.List;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class MainSelectTest {

    @Resource(name = "tpURLFactory")
    private URLFactory tpURLFactory;

    private String personId = "100079421";

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
    private NotebookIdSelector notebookIdSelector;

    @Resource(name = "notebookNameSelector")
    private TextObjectSelector notebookNameSelector;

    @Resource(name = "notebookBeginEndDateSelector")
    private PropertySelector<List<LocalDate>> beginEndDateSelector;

    @Autowired
    private ImagePathFactory imagePathFactory;

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
        Document document = Jsoup.connect(personURL).get();
        Optional optionalPid = Optional.of(personId);

        Element element = document.select(".notebooks .notebook").last();

        String id = notebookIdSelector.select(element, optionalPid);
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
}
