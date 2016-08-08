package com.acgsior.docx;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Created by mqin on 8/8/16.
 */
public interface IDocumentWriter <T> {

	void output(XWPFDocument document, T t);

}
