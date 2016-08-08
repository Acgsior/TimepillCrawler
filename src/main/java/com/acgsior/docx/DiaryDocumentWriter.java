package com.acgsior.docx;

import com.acgsior.bootstrap.ILocalDateTimeToStringConverter;
import com.acgsior.model.Diary;
import com.acgsior.image.ImageDto;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by Yove on 16/08/07.
 */
public class DiaryDocumentWriter implements IDocumentWriter<Diary>, ILocalDateTimeToStringConverter {

	static Logger logger = LoggerFactory.getLogger(DiaryDocumentWriter.class);

	static DateTimeFormatter DIARY_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.getDefault());
	static DateTimeFormatter DIARY_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh : mm a", Locale.getDefault());

	@Override
	public void output(XWPFDocument doc, Diary diary) {
		XWPFParagraph paragraph1 = doc.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.LEFT);

		// header
		XWPFRun header = paragraph1.createRun();
		String diaryHeader = makeHeader(diary);
		header.setText(diaryHeader);
		header.setBold(true);
		header.addCarriageReturn();

		// comments
		XWPFRun comments = paragraph1.createRun();
		comments.setText(makeComments(diary));

		// image
		if (StringUtils.isNotBlank(diary.getImage())) {
			ImageDto imageDto = ImageDto.newInstance(diary.getImage());
			XWPFParagraph paragraph2 = doc.createParagraph();
			XWPFRun avatar = paragraph2.createRun();
			try (FileInputStream fis = imageDto.getImageInputStream()) {
				avatar.addPicture(fis, imageDto.getFormat(), imageDto.getImage(), imageDto.getWidth(), imageDto.getHeight());
			} catch (InvalidFormatException | IOException e) {
				logger.error("Insert avatar failed for diary: " + diary.getImage(), e);
				// TODO add default avatar
			}
			avatar.addCarriageReturn();
		}

		XWPFParagraph paragraph3 = doc.createParagraph();
		paragraph3.setAlignment(ParagraphAlignment.LEFT);

		// content
		XWPFRun content = paragraph3.createRun();
		Iterator<String> contentIt = Splitter.on('\n').split(diary.getContent()).iterator();
		while (contentIt.hasNext()) {
			content.setText(contentIt.next());
			content.addCarriageReturn();
		}
	}

	protected String makeHeader(Diary diary) {
		return Joiner.on("\t\t\t\t").skipNulls().join(Lists.newArrayList(localTimeToString(diary.getDiaryTime(), DIARY_TIME_FORMATTER),
				localDateToString(diary.getDiaryDate(), DIARY_DATE_FORMATTER), diary.getDiaryDate().getDayOfWeek(),
				diary.getNotebookName()));
	}

	protected String makeComments(Diary diary) {
		return new StringBuilder().append("\u56de\u590d(").append(diary.getCommentCount()).append(")").toString();
	}
}
