package com.acgsior.docx;

import com.acgsior.bootstrap.ILocalDateTimeToStringConverter;
import com.acgsior.image.ImageDto;
import com.acgsior.model.Person;
import com.google.common.base.Splitter;
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
 * Created by mqin on 8/8/16.
 */
public class PersonDocumentWriter implements IDocumentWriter<Person>, ILocalDateTimeToStringConverter {

	static Logger logger = LoggerFactory.getLogger(PersonDocumentWriter.class);

	static DateTimeFormatter PERSON_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.getDefault());

	@Override
	public void output(XWPFDocument doc, Person person) {

		// name
		XWPFParagraph paragraph1 = doc.createParagraph();
		XWPFRun name = paragraph1.createRun();
		name.setText(person.getName());
		name.setBold(true);
		name.setFontSize(20);

		// avatar
		if (StringUtils.isNotBlank(person.getAvatar())) {
			ImageDto imageDto = ImageDto.newInstance(person.getAvatar());
			XWPFParagraph paragraph2 = doc.createParagraph();
			XWPFRun avatar = paragraph2.createRun();
			try (FileInputStream fis = imageDto.getImageInputStream()) {
				avatar.addPicture(fis, imageDto.getFormat(), imageDto.getImage(), imageDto.getWidth(), imageDto.getHeight());
			} catch (InvalidFormatException | IOException e) {
				logger.error("Insert avatar failed for person: " + person.getAvatar(), e);
				// TODO add default avatar
			}
			avatar.addBreak();
		}

		XWPFParagraph paragraph3 = doc.createParagraph();
		paragraph3.setAlignment(ParagraphAlignment.LEFT);

		// join date
		XWPFRun joinDate = paragraph3.createRun();
		joinDate.setText(makeJoinDate(person));
		joinDate.setBold(true);
		joinDate.addCarriageReturn();

		// person description
		XWPFRun description = paragraph3.createRun();
		Iterator<String> contentIt = Splitter.on('\n').split(person.getDescription()).iterator();
		while (contentIt.hasNext()) {
			description.setText(contentIt.next());
			description.addCarriageReturn();
		}
	}

	protected String makeJoinDate(Person person) {
		return new StringBuilder().append(person.getRegisterDate().format(PERSON_DATE_FORMATTER)).append(" \u52a0\u5165").toString();
	}
}
