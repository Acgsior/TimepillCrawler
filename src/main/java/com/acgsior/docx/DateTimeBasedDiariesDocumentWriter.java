package com.acgsior.docx;

import com.acgsior.model.Diary;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mqin on 8/8/16.
 */
public class DateTimeBasedDiariesDocumentWriter implements IDocumentWriter<TreeMap<String, Diary>> {

	private DiaryDocumentWriter diaryDocumentWriter;

	@Override
	public void output(XWPFDocument document, TreeMap<String, Diary> diaryMap) {
		String year = ""; // timepill had not been created yet

		Iterator<Map.Entry<String, Diary>> it = diaryMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Diary> entry = it.next();

			String yearOfKey = getYearOfDateKey(entry.getKey());
			if (!compareYearOfDateKey(year, yearOfKey)) {

				year = yearOfKey;

				XWPFParagraph paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.LEFT);
				paragraph.setPageBreak(true);

				XWPFRun yearLine = paragraph.createRun();
				yearLine.setText(makeYearLine(year));
				yearLine.setFontSize(20);
				yearLine.setBold(true);
				yearLine.addCarriageReturn();
			}

			diaryDocumentWriter.output(document, entry.getValue());
		}
	}

	protected String makeYearLine(final String year) {
		return new StringBuilder().append(" - ").append(year).append(" - ").toString();
	}

	protected boolean compareYearOfDateKey(String year, String yearOfKey) {
		return StringUtils.equals(year, yearOfKey);
	}

	protected String getYearOfDateKey(String key) {
		return Splitter.on('-').omitEmptyStrings().splitToList(key).get(0);
	}

	public DiaryDocumentWriter getDiaryDocumentWriter() {
		return diaryDocumentWriter;
	}

	public void setDiaryDocumentWriter(DiaryDocumentWriter diaryDocumentWriter) {
		this.diaryDocumentWriter = diaryDocumentWriter;
	}
}
