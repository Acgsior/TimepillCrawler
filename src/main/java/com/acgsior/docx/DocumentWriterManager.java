package com.acgsior.docx;

import com.acgsior.cache.CacheManager;
import com.acgsior.cache.DatetimeBasedCache;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Created by Yove on 16/08/08.
 */
public class DocumentWriterManager {

	private PersonDocumentWriter personDocumentWriter;

	private NotebooksDocumentWriter notebooksDocumentWriter;

	private DateTimeBasedDiariesDocumentWriter dateTimeBasedDiariesDocumentWriter;

	private CacheManager cacheManager;

	public void writeByDateTime(XWPFDocument document) {
		DatetimeBasedCache cache = cacheManager.getDatetimeBasedCache();
		getPersonDocumentWriter().output(document, cache.getPersonCache());
		getNotebooksDocumentWriter().output(document, cache.getNotebookCache());
		getDateTimeBasedDiariesDocumentWriter().output(document, cache.getDiaryCache());
	}

	public PersonDocumentWriter getPersonDocumentWriter() {
		return personDocumentWriter;
	}

	public void setPersonDocumentWriter(final PersonDocumentWriter personDocumentWriter) {
		this.personDocumentWriter = personDocumentWriter;
	}

	public NotebooksDocumentWriter getNotebooksDocumentWriter() {
		return notebooksDocumentWriter;
	}

	public void setNotebooksDocumentWriter(final NotebooksDocumentWriter notebooksDocumentWriter) {
		this.notebooksDocumentWriter = notebooksDocumentWriter;
	}

	public DateTimeBasedDiariesDocumentWriter getDateTimeBasedDiariesDocumentWriter() {
		return dateTimeBasedDiariesDocumentWriter;
	}

	public void setDateTimeBasedDiariesDocumentWriter(final DateTimeBasedDiariesDocumentWriter dateTimeBasedDiariesDocumentWriter) {
		this.dateTimeBasedDiariesDocumentWriter = dateTimeBasedDiariesDocumentWriter;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(final CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
