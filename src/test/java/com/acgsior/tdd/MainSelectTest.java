package com.acgsior.tdd;

import com.acgsior.factory.ImagePathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.image.ImageType;
import com.acgsior.selector.PropertySelector;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ImagePathFactory imagePathFactory;

	@Test
	public void personURLTest() throws IOException {
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
}
