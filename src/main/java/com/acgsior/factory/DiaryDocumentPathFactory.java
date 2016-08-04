package com.acgsior.factory;

import com.acgsior.bootstrap.ICleanFolder;
import com.acgsior.bootstrap.ICreateFolder;
import com.acgsior.exception.CrawlerInitialException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by mqin on 8/3/16.
 */
public class DiaryDocumentPathFactory implements ICreateFolder, ICleanFolder {

    private boolean cleanFoldFirst;

    private String basePath;

    public DiaryDocumentPathFactory(String basePath, boolean cleanFoldFirst) {
        this.basePath = basePath;
        this.cleanFoldFirst = cleanFoldFirst;
        createDiaryDocumentFileFolder(Optional.empty());
    }

    public void createDiaryDocumentFileFolder(Optional<String> personName) {
        Path dir = Paths.get(basePath.concat(personName.orElse("")));
        if (Files.notExists(dir, LinkOption.NOFOLLOW_LINKS)) {
            createFolder(dir);
        }
        if (cleanFoldFirst) {
            try {
                cleanFolder(dir);
            } catch (IOException e) {
                String msg = String.format("Clean diary document file directory failed for path: %s", dir);
                throw new CrawlerInitialException(msg);
            }
        }
    }

    public void createDiaryDocument(XWPFDocument doc, Optional<String> personName, Optional<String> documentName) throws IOException {
        createDiaryDocumentFileFolder(personName);

        String fileDir = basePath.concat(personName.orElse("")).concat("/").concat(documentName.orElse("untitled")).concat(".docx");
        Files.createFile(Paths.get(fileDir), getMacFolderAttributes());

        OutputStream out = new FileOutputStream(fileDir);
        doc.write(out);
        out.close();
    }

    public void setCleanFoldFirst(boolean cleanFoldFirst) {
        this.cleanFoldFirst = cleanFoldFirst;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
