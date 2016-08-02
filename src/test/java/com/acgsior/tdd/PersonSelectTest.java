package com.acgsior.tdd;

import com.acgsior.factory.URLFactory;
import com.acgsior.model.Person;
import com.acgsior.selector.PropertySelector;
import com.acgsior.selector.impl.person.PersonObjectSelector;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Yove on 16/07/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class PersonSelectTest {

    private String personId = "100079421";

    @Resource(name = "tpURLFactory")
    private URLFactory tpURLFactory;

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
        Assert.assertEquals("100079421", person.getId());
        Assert.assertEquals("Excelsior†", person.getName());
        Assert.assertEquals(LocalDate.of(2012, 4, 5), person.getRegisterDate());
    }
}
