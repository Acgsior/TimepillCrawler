package com.acgsior.tdd;

import com.acgsior.bootstrap.ICleanFolder;
import com.acgsior.bootstrap.ICreateFolder;
import com.acgsior.docx.DiaryDocumentWriter;
import com.acgsior.factory.DiaryDocumentPathFactory;
import com.acgsior.factory.URLFactory;
import com.acgsior.selector.impl.diary.DiaryObjectSelector;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private DiaryDocumentWriter diaryWriter;

    public static void main(String[] args) throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();

        XWPFRun r = p.createRun();
        args = new String[]{"/Users/mqin/topit_me/112000607109b9ce07o.jpg"};

        for (String imgFile : args) {
            int format;

            if (imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
            else if (imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
            else if (imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
            else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
            else if (imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
            else if (imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
            else if (imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
            else if (imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
            else if (imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
            else if (imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
            else if (imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
            else {
                System.err.println("Unsupported picture: " + imgFile +
                        ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
                continue;
            }

            r.setText(imgFile);
            r.addBreak();
            r.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
            r.addBreak(BreakType.PAGE);
        }

        FileOutputStream out = new FileOutputStream("images.docx");
        doc.write(out);
        out.close();
    }

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
        r1.addCarriageReturn();

        XWPFParagraph p2 = doc.createParagraph();
        XWPFRun r2 = p2.createRun();
        String image = "/Users/mqin/topit_me/112000607109b9ce07o.jpg";
//		FileInputStream avatar = new FileInputStream("/Users/Yove/Downloads/101237285739d4fc74.jpg");
        FileInputStream avatar = new FileInputStream(image);
        int imageFormat = getImageFormat(image);
//        BufferedImage bi = ImageIO.read(avatar);
//        r2.setText("/Users/mqin/topit_me/112000607109b9ce07o.jpg");
//        r2.addBreak();
//        r2.addPicture(avatar, imageFormat, "/Users/mqin/topit_me/112000607109b9ce07o.jpg", bi.getWidth(), bi.getHeight());
        r2.addPicture(avatar, imageFormat, image, Units.toEMU(200), Units.toEMU(200));
        r2.addBreak();
//        r2.addPicture(avatar, imageFormat, image, bi.getWidth(), bi.getHeight());
        r2.addBreak(BreakType.PAGE);
//        avatar.close();

        XWPFParagraph p3 = doc.createParagraph();
        XWPFRun r3 = p3.createRun();
        r3.setText("2012-04-05 加入");
        r3.setCapitalized(true);
        r3.setBold(true);
        r3.addBreak();
        p3.setPageBreak(true);

        XWPFParagraph p4 = doc.createParagraph();
        XWPFRun r4 = p4.createRun();
        r4.setText("2012-04-05 加入");
        r4.setCapitalized(true);
        r4.setBold(true);
        r4.addBreak();
        p4.setPageBreak(true);

//		String dateNotebookURL = tpURLFactory.getURL(URLFactory.DATE_NOTEBOOK, notebookId, diaryDate).get();
//		Optional optionalPid = Optional.of(notebookId);
//		org.jsoup.nodes.Document document = Jsoup.connect(dateNotebookURL).get();
//		List<Diary> dairies = diarySelector.select(document, optionalPid);
//
//		Notebook mockNotebook = Notebook.newInstance(notebookId);
//		mockNotebook.setName("- Closed Note -");
//
//		dairies.forEach(diary -> diaryWriter.output(doc, diary, mockNotebook));

        diaryDocumentPathFactory.createDiaryDocument(doc, Optional.of("testMan"), Optional.of("testMan"));
    }


    private int getImageFormat(String imgFile) {
        int format = 0;
        if (imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
        else if (imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
        else if (imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
        else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
        else if (imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
        else if (imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
        else if (imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
        else if (imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
        else if (imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
        else if (imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
        else if (imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
        return format;
    }
}