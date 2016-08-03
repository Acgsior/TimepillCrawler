package com.acgsior.tdd;

import com.acgsior.bootstrap.ICleanFolder;
import com.acgsior.bootstrap.ICreateFolder;
import com.acgsior.factory.DiaryDocumentPathFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mqin on 8/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class POITest implements ICreateFolder, ICleanFolder {

    @Resource(name = "diaryDocumentPathFactory")
    private DiaryDocumentPathFactory diaryDocumentPathFactory;

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
    public void wordDocumentGenerateTest() {

    }
}
