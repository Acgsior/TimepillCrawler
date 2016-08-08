package com.acgsior.docx;

import com.acgsior.bootstrap.ILocalDateTimeToStringConverter;
import com.acgsior.model.Person;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by mqin on 8/8/16.
 */
public class PersonDocumentWriter implements IDocumentWriter<Person>, ILocalDateTimeToStringConverter {

    static DateTimeFormatter PERSON_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.getDefault());

    @Override
    public void output(XWPFDocument doc, Person person) {
        XWPFParagraph paragraph = doc.createParagraph();

        // name
        XWPFRun name = paragraph.createRun();
        name.setText(person.getName());
        name.setBold(true);
        name.setFontSize(20);
        name.addCarriageReturn();

        // avatar
        if (StringUtils.isNoneBlank(person.getAvatar())) {
            // TODO avatar
        }

        // join date
        XWPFRun joinDate = paragraph.createRun();
        joinDate.setText(makeJoinDate(person));
        joinDate.addCarriageReturn();

        // person description
        XWPFRun description = paragraph.createRun();
        Iterator<String> contentIt = Splitter.on('\n').split(person.getDescription()).iterator();
        while (contentIt.hasNext()) {
            description.setText(contentIt.next());
            description.addCarriageReturn();
        }

        // TODO test page break happens after paragraph or before
        paragraph.setPageBreak(true);
    }

    protected String makeJoinDate(Person person) {
        return new StringBuilder().append(person.getRegisterDate().format(PERSON_DATE_FORMATTER)).append(" \u52a0\u5165").toString();
    }
}
