package com.acgsior.docx;

import com.acgsior.bootstrap.ILocalDateTimeToStringConverter;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by Yove on 16/08/07.
 */
public class DiaryWriter implements ILocalDateTimeToStringConverter {

	static DateTimeFormatter DIARY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.getDefault());
	static DateTimeFormatter DIARY_TIME_FORMATTER = DateTimeFormatter.ofPattern("h : m a", Locale.getDefault());


	public void output(XWPFDocument doc, Diary diary, Notebook notebook) {
		XWPFParagraph paragraph = doc.createParagraph();

		// header
		XWPFRun header = paragraph.createRun();
		String diaryHeader = makeHeader(diary, notebook);
		header.setText(diaryHeader);
		header.setBold(true);
		header.addCarriageReturn();

		// image
		if (StringUtils.isNoneBlank(diary.getImage())) {
			//TODO image
		}

		// content
		XWPFRun content = paragraph.createRun();
		Iterator<String> contentIt = Splitter.on('\n').split(diary.getContent()).iterator();
		while (contentIt.hasNext()) {
			content.setText(contentIt.next());
			content.addCarriageReturn();
		}

		// comments
		XWPFRun comments = paragraph.createRun();
		comments.setText(makeComments(diary));
		comments.addCarriageReturn();
	}

	protected String makeHeader(Diary diary, Notebook notebook) {
		return Joiner.on('\t').skipNulls().join(Lists.newArrayList(localTimeToString(diary.getDiaryTime(), DIARY_TIME_FORMATTER),
				localDateToString(diary.getDiaryDate(), DIARY_DATE_FORMATTER), diary.getDiaryDate().getDayOfWeek(),
				notebook.getNotebookOutputName()));
	}

	protected String makeComments(Diary diary) {
		return new StringBuilder().append("\u56de\u590d(").append(diary.getCommentCount()).append(")").toString();
	}
}
