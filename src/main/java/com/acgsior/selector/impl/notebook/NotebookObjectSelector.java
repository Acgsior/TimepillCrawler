package com.acgsior.selector.impl.notebook;

import com.acgsior.bootstrap.ICachedSelector;
import com.acgsior.bootstrap.IStandardizeURL;
import com.acgsior.factory.BeanFactory;
import com.acgsior.model.Diary;
import com.acgsior.model.Notebook;
import com.acgsior.provider.DocumentProvider;
import com.acgsior.provider.ExecutorProvider;
import com.acgsior.selector.ObjectSelector;
import com.acgsior.selector.impl.diary.DiaryLinksSelector;
import com.acgsior.selector.impl.diary.DiaryObjectSelector;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by Yove on 16/07/03.
 */
public class NotebookObjectSelector extends ObjectSelector<List<Notebook>> implements ICachedSelector<List<Notebook>>, IStandardizeURL {

    private DiaryObjectSelector diarySelector;

    private DiaryLinksSelector diaryLinksSelector;

    @Override
    public List<Notebook> select(final Document document, final Optional parentId) {
        Elements elements = document.select(getPattern());
        List<Notebook> notebooks = new ArrayList<>();

        elements.forEach(element -> {
            String notebookId = getIdSelector().select(element, parentId);
            Notebook notebook = Notebook.newInstance(notebookId);
            notebook.setParent(String.valueOf(parentId.get()));

            Arrays.stream(getSyncSelectors()).forEach(selector -> {
                Object value = selector.select(element, notebook.getOptionalId());
                BeanFactory.setPropertyValueSafely(notebook, selector.getProperty(), value);
            });
            notebooks.add(notebook);
        });

        cache(notebooks);

        notebooks.parallelStream().filter(notebook -> notebook.isNotebookExpired())
                .forEach(notebook -> {
                    Optional<Document> notebookDocOptional = DocumentProvider.fetch(standardizeURL(notebook.getLocation()));
                    if (notebookDocOptional.isPresent()) {
                        List<String> diaryLinks = diaryLinksSelector.select(notebookDocOptional.get(), notebook.getOptionalId());

                        List<CompletableFuture<List<Diary>>> diarySelectFutures = diaryLinks.stream()
                                .map(diaryLink -> DocumentProvider.fetch(diaryLink))
                                .filter(Optional::isPresent)
                                .map(diaryDocOptional -> CompletableFuture.supplyAsync(
                                        () -> diarySelector.select(diaryDocOptional.get(), notebook.getOptionalId()),
                                        ExecutorProvider.getDiaryExecutor()))
                                .collect(Collectors.toList());

                        diarySelectFutures.forEach(CompletableFuture::join);
                    }
                });

        return notebooks;
    }

    @Override
    public void cache(List<Notebook> value) {
        value.forEach(notebook -> {
            getCache().cacheNotebook(notebook);
        });
    }

    public void setDiarySelector(DiaryObjectSelector diarySelector) {
        this.diarySelector = diarySelector;
    }

    public void setDiaryLinksSelector(DiaryLinksSelector diaryLinksSelector) {
        this.diaryLinksSelector = diaryLinksSelector;
    }
}
