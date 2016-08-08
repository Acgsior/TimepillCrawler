package com.acgsior.docx;

import com.acgsior.model.Notebook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.Map;

/**
 * Created by mqin on 8/8/16.
 */
public class NotebooksDocumentWriter implements IDocumentWriter<Map<String, Notebook>> {

    private NotebookDocumentWriter notebookDocumentWriter;

    @Override
    public void output(XWPFDocument document, Map<String, Notebook> notebookMap) {
        document.createParagraph().setPageBreak(true);
        notebookMap.forEach((key, value) -> notebookDocumentWriter.output(document, value));
    }

    public NotebookDocumentWriter getNotebookDocumentWriter() {
        return notebookDocumentWriter;
    }

    public void setNotebookDocumentWriter(NotebookDocumentWriter notebookDocumentWriter) {
        this.notebookDocumentWriter = notebookDocumentWriter;
    }
}
