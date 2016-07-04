package com.acgsior.tdd;

import com.acgsior.factory.ImagePathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.image.ImageType;
import com.acgsior.model.Notebook;
import com.acgsior.selector.PropertySelector;
import com.acgsior.selector.impl.NotebookObjectSelector;
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
    public void personInfoSelectTest() throws IOException {
        String personURL = tpURLFactory.getURL(URLFactory.PERSON, personId).get();
        Assert.assertEquals(personURL, "http://timepill.net/people/100079421");

        Document document = Jsoup.connect(personURL).get();

        String personName = personNameSelector.select(document, Optional.empty());
        Assert.assertEquals(personName, "Excelsior†");

        LocalDate registerDate = registerDateSelector.select(document, Optional.empty());
        Assert.assertEquals(registerDate, LocalDate.of(2012, 4, 5));

        String personDescription = personDescriptionSelector.select(document, Optional.empty());
        Assert.assertTrue(StringUtils.isNoneBlank(personDescription));

        String avatarImageName = avatarSelector.select(document, Optional.of("100079421"));
        Assert.assertEquals(avatarImageName, "100079421");
    }

    @Test
    public void imagePathFactoryWorkTest() {
        String extension = imagePathFactory.getImageExtension("http//s.timepill.net/user_icon/20015/b100079421.jpg?v=43");
        Assert.assertEquals(extension, "jpg");

        String path = imagePathFactory.getPathWithoutExtension(ImageType.IMAGE, "100079421");
        Assert.assertEquals(path, "/Users/Yove/Temp/timepill/image/100079421");
    }

    @Test
    public void notebooksSelectTest() throws IOException {
        String personURL = tpURLFactory.getURL(URLFactory.PERSON, personId).get();
        Assert.assertEquals(personURL, "http://timepill.net/people/100079421");
        Document document = Jsoup.connect(personURL).get();
        Optional optionalPid = Optional.of(personId);

        Element element = document.select(".notebooks .notebook").last();

        String id = notebookIdSelector.select(element, optionalPid);
        Assert.assertEquals(id, "95005");

        String notebookName = notebookNameSelector.select(element, optionalPid);
        Assert.assertEquals(notebookName, "- Closed Note -");

        List<LocalDate> dates = beginEndDateSelector.select(element, optionalPid);
        Assert.assertNotNull(dates);
        Assert.assertEquals(dates.size(), 2);
        Assert.assertEquals(dates.get(0), LocalDate.of(2010, 7, 29));
        Assert.assertEquals(dates.get(1), LocalDate.of(2016, 3, 31));

        List<Notebook> notebooks = notebookSelector.select(document, optionalPid);
        Assert.assertTrue(notebooks.size() > 0);

        Notebook notebook = Iterables.getLast(notebooks);
        Assert.assertEquals(notebook.getTid(), "95005");
        Assert.assertEquals(notebook.getName(), "- Closed Note -");
        Assert.assertEquals(notebook.getBegin(), LocalDate.of(2010, 7, 29));
        Assert.assertEquals(notebook.getEnd(), LocalDate.of(2016, 3, 31));
    }
}
