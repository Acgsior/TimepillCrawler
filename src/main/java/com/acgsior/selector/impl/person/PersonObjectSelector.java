package com.acgsior.selector.impl.person;

import com.acgsior.factory.BeanFactory;
import com.acgsior.model.Person;
import com.acgsior.selector.ICachedSelector;
import com.acgsior.selector.ObjectSelector;
import com.acgsior.selector.impl.notebook.NotebookObjectSelector;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by mqin on 7/4/16.
 */
public class PersonObjectSelector extends ObjectSelector<Person> implements ICachedSelector<Person> {

    private NotebookObjectSelector notebookSelector;

    @Override
    public Person select(Document document, Optional parentId) {
        Element element = document.select(getPattern()).get(getElementIndex());
        Person person = Person.newInstance((String) parentId.get());

        Arrays.stream(getSyncSelectors()).forEach(selector -> {
            Object value = selector.select(element, Optional.of(person.getId()));
            BeanFactory.setPropertyValueSafely(person, selector.getProperty(), value);
        });

        cache(person);

        notebookSelector.select(document, parentId);
        return person;
    }

    @Override
    public void cache(Person value) {
        // logger.info(value.toString());
        getCacheManager().cachePerson(value);
    }

    public void setNotebookSelector(NotebookObjectSelector notebookSelector) {
        this.notebookSelector = notebookSelector;
    }
}
