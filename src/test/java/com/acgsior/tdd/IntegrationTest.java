package com.acgsior.tdd;

import com.acgsior.bootstrap.ICrawledDataCacheLogger;
import com.acgsior.cache.CacheManager;
import com.acgsior.docx.DocumentWriterManager;
import com.acgsior.factory.DiaryDocumentPathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.selector.impl.person.PersonObjectSelector;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by mqin on 8/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class IntegrationTest {

	private String pidOfJane = "100123750";
	private String pidOfMine = "100079421";

	@Resource(name = "URLFactory")
	private URLFactory URLFactory;

	@Resource(name = "personSelector")
	private PersonObjectSelector personSelector;

	@Resource(name = "diaryDocumentPathFactory")
	private DiaryDocumentPathFactory diaryDocumentPathFactory;

	@Resource(name = "documentWriterManager")
	private DocumentWriterManager documentWriterManager;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void test() throws IOException {
		String personURL = URLFactory.getURL(URLFactory.PERSON, pidOfJane).get();
		Document document = Jsoup.connect(personURL).get();

		personSelector.select(document, Optional.of(pidOfMine));

		Path dir = Paths.get(diaryDocumentPathFactory.getBasePath());
		//diaryDocumentPathFactory.setCleanFoldFirst(true);
		//diaryDocumentPathFactory.cleanFolder(dir);
		diaryDocumentPathFactory.createDiaryDocumentFileFolder(Optional.of(pidOfJane));

		((ICrawledDataCacheLogger) cacheManager.getCache()).logCacheStatus();

		XWPFDocument doc = new XWPFDocument();
		documentWriterManager.writeByDateTime(doc);

		diaryDocumentPathFactory.createDiaryDocument(doc, Optional.of(pidOfMine), Optional.of(pidOfMine));
	}
}
