package com.acgsior.selector.impl;

import com.acgsior.factory.BeanFactory;
import com.acgsior.model.Notebook;
import com.acgsior.selector.ObjectSelector;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Yove on 16/07/03.
 */
public class NotebookObjectSelector extends ObjectSelector {

	@Override
	public Notebook select(final Document document, final Optional parentId) {
		Element element = document.select(getPattern()).get(getElementIndex());
		Notebook notebook = Notebook.newInstance();
		Arrays.stream(getSyncSelectors()).forEach(selector -> {
			Object value = selector.select(element, Optional.of(notebook.getUid()));
			BeanFactory.setPropertyValueSafely(notebook, selector.getProperty(), value);
		});
		return notebook;
	}
}
