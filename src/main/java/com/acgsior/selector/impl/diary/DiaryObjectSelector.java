package com.acgsior.selector.impl.diary;

import com.acgsior.bootstrap.ICachedSelector;
import com.acgsior.cache.DatetimeBasedCache;
import com.acgsior.factory.BeanFactory;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.selector.ObjectSelector;
import com.acgsior.selector.impl.DateObjectSelector;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * TODO
 * 1. invoke selector to select next month link, if exist generate new diary selector to invoke
 * 2. new diary selector should be submit to executor
 * 3. then select diary content
 * <p>
 * Created by Yove on 7/4/16.
 */
public class DiaryObjectSelector extends ObjectSelector<List<Diary>> implements ICachedSelector<List<Diary>> {

	@Resource(name = "diaryDateSelector")
	private DateObjectSelector diaryDateSelector;

	@Override
	public List<Diary> select(Document document, Optional<String> parentId) {
		Elements elements = document.select(getPattern());
		List<Diary> diaries = new ArrayList<>();

		LocalDate diaryDate = diaryDateSelector.select(document, Optional.empty());
		String tmpNotebookName = null;

		if (getCache() instanceof DatetimeBasedCache) {
			DatetimeBasedCache cache = (DatetimeBasedCache) getCache();
			Optional<Notebook> notebookOptional = cache.getNotebookCache().values().stream()
					.filter(notebook -> parentId.isPresent() && StringUtils.equals(parentId.get(), notebook.getId())).findFirst();
			if (notebookOptional.isPresent()) {
				tmpNotebookName = notebookOptional.get().getName();
			}
		}

		final String notebookName = tmpNotebookName;

		elements.forEach(element -> {
			Diary diary = Diary.newInstance(getIdSelector().select(element, parentId));
			diary.setParent(parentId.orElse(""));

			Arrays.stream(getSyncSelectors()).forEach(selector -> {
				Object value = selector.select(element, Optional.of(diary.getId()));
				BeanFactory.setPropertyValueSafely(diary, selector.getProperty(), value);
			});

			diary.setDiaryDate(diaryDate);
			diary.setNotebookName(notebookName);

			diaries.add(diary);
		});

		cache(diaries);

		return diaries;
	}

	@Override
	public void cache(List<Diary> value) {
		value.forEach(diary -> getCache().cacheDiary(diary));
	}

	public void setDiaryDateSelector(DateObjectSelector diaryDateSelector) {
		this.diaryDateSelector = diaryDateSelector;
	}
}
