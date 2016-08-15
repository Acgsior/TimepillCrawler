package com.acgsior.docx;

import com.acgsior.bootstrap.ILocalDateTimeToStringConverter;
import com.acgsior.model.Notebook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Yove on 8/8/16.
 */
public class NotebookDocumentWriter implements IDocumentWriter<Notebook>, ILocalDateTimeToStringConverter {

	static DateTimeFormatter NOTEBOOK_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.getDefault());

	@Override
	public void output(XWPFDocument doc, Notebook notebook) {
		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		// name
		XWPFRun line = paragraph.createRun();
		line.setText(makeNotebookLine(notebook));
		line.setBold(true);
	}

	protected String makeNotebookLine(Notebook notebook) {
		return new StringBuilder().append("+ ").append(notebook.getNotebookOutputName()).append(" (")
				.append(notebook.getBegin().format(NOTEBOOK_DATE_FORMATTER)).append(" - ")
				.append(notebook.getEnd().format(NOTEBOOK_DATE_FORMATTER)).append(")").toString();
	}
}
