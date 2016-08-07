package com.acgsior.tdd;

import com.acgsior.bootstrap.ICleanFolder;
import com.acgsior.bootstrap.ICreateFolder;
import com.acgsior.docx.DiaryWriter;
import com.acgsior.factory.DiaryDocumentPathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.selector.impl.diary.DiaryObjectSelector;
import com.google.common.base.Splitter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by mqin on 8/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class POITest implements ICreateFolder, ICleanFolder {

	private String notebookId = "95005";
	private String diaryDate = "2010-08-04";

	@Resource(name = "tpURLFactory")
	private URLFactory tpURLFactory;

	@Resource(name = "diaryDocumentPathFactory")
	private DiaryDocumentPathFactory diaryDocumentPathFactory;

	@Resource(name = "diarySelector")
	private DiaryObjectSelector diarySelector;

	@Resource(name = "diaryWriter")
	private DiaryWriter diaryWriter;

	@Test
	public void wordDocumentFolderTest() throws IOException {
		Path dir = Paths.get(diaryDocumentPathFactory.getBasePath());
		Path fwfDir = Paths.get(diaryDocumentPathFactory.getBasePath().concat("folder_with_file/"));
		Path ffDir = Paths.get(diaryDocumentPathFactory.getBasePath().concat("folder_with_file/file.txt"));
		Path fwofDir = Paths.get(diaryDocumentPathFactory.getBasePath().concat("folder_without_file/"));
		Path fDir = Paths.get(diaryDocumentPathFactory.getBasePath().concat("file.txt"));

		createFolder(fwfDir);
		createFolder(fwofDir);
		Files.createFile(ffDir, getMacFolderAttributes());
		Files.createFile(fDir, getMacFolderAttributes());

		diaryDocumentPathFactory.setCleanFoldFirst(true);
		diaryDocumentPathFactory.cleanFolder(dir);

		Assert.assertEquals(Files.walk(dir).count(), 1);
	}

	@Test
	public void wordDocumentGenerateTest() throws IOException, InvalidFormatException {
		Path dir = Paths.get(diaryDocumentPathFactory.getBasePath());
		diaryDocumentPathFactory.setCleanFoldFirst(true);
		diaryDocumentPathFactory.cleanFolder(dir);
		diaryDocumentPathFactory.createDiaryDocumentFileFolder(Optional.of("testMan/"));

		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph p1 = doc.createParagraph();

		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setText("Excelsior†");
		r1.setFontSize(20);

		XWPFParagraph p2 = doc.createParagraph();
		XWPFRun r2 = p2.createRun();
		FileInputStream avatar = new FileInputStream("/Users/Yove/Downloads/101237285739d4fc74.jpg");
		BufferedImage bi = ImageIO.read(avatar);
		r2.addPicture(avatar, XWPFDocument.PICTURE_TYPE_JPEG, "Avatar", bi.getWidth(), bi.getHeight());
		avatar.close();

		XWPFParagraph p3 = doc.createParagraph();
		XWPFRun r3 = p3.createRun();
		r3.setText("2012-04-05 加入");
		r3.setCapitalized(true);
		r3.setBold(true);

		String dateNotebookURL = tpURLFactory.getURL(URLFactory.DATE_NOTEBOOK, notebookId, diaryDate).get();
		Optional optionalPid = Optional.of(notebookId);
		org.jsoup.nodes.Document document = Jsoup.connect(dateNotebookURL).get();
		List<Diary> dairies = diarySelector.select(document, optionalPid);

		Notebook mockNotebook = Notebook.newInstance(notebookId);
		mockNotebook.setName("- Closed Note -");

		dairies.forEach(diary -> diaryWriter.output(doc, diary, mockNotebook));

		diaryDocumentPathFactory.createDiaryDocument(doc, Optional.of("testMan"), Optional.of("testMan"));
	}

}