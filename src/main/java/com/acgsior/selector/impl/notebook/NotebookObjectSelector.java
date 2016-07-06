package com.acgsior.selector.impl.notebook;

import com.acgsior.factory.BeanFactory;
import com.acgsior.model.Notebook;
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
public class NotebookObjectSelector extends ObjectSelector implements ICachedSelector {

    @Override
    public List<Notebook> select(final Document document, final Optional parentId) {
        Elements elements = document.select(getPattern());
        List<Notebook> notebooks = new ArrayList<>();

        elements.forEach(element -> {
            String notebookId = getIdSelector().select(element, parentId);
            Notebook notebook = Notebook.newInstance(notebookId);
            Arrays.stream(getSyncSelectors()).forEach(selector -> {
                Object value = selector.select(element, Optional.of(notebookId));
                BeanFactory.setPropertyValueSafely(notebook, selector.getProperty(), value);
            });
            // FIXME set parent
            notebooks.add(notebook);
        });
        return notebooks;
    }

    @Override
    public void cache(Object value) {
    }
}
