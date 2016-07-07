package com.acgsior.selector.impl.notebook;

import com.acgsior.factory.BeanFactory;
import com.acgsior.factory.IDiarySelectorGenerator;
import com.acgsior.model.Notebook;
import com.acgsior.provider.DocumentProvider;
import com.acgsior.selector.ICachedSelector;
import com.acgsior.selector.ObjectSelector;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Yove on 16/07/03.
 */
public class NotebookObjectSelector extends ObjectSelector implements ICachedSelector, IDiarySelectorGenerator {

	@Override
	public List<Notebook> select(final Document document, final Optional parentId) {
		Elements elements = document.select(getPattern());
		List<Notebook> notebooks = new ArrayList<>();

		elements.forEach(element -> {
			String notebookId = getIdSelector().select(element, parentId);
			Notebook notebook = Notebook.newInstance(notebookId);
			notebook.setParent(String.valueOf(parentId.get()));

			Arrays.stream(getSyncSelectors()).forEach(selector -> {
				Object value = selector.select(element, Optional.of(notebookId));
				BeanFactory.setPropertyValueSafely(notebook, selector.getProperty(), value);
			});
			notebooks.add(notebook);
		});

		cache(notebooks);

		notebooks.forEach(notebook -> {
			Optional<Document> diaryFrontPage = DocumentProvider.fetch(notebook.getLocation());
			if (diaryFrontPage.isPresent()) {
				generateDiarySelector().select(diaryFrontPage.get(), Optional.of(notebook.getId()));
			}
		});
		return notebooks;
	}

	@Override
	public void cache(Object value) {
	}


}
