package com.acgsior.selector.impl.diary;

import com.acgsior.selector.impl.TextObjectSelector;
import com.google.common.base.Splitter;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Optional;

/**
 * Created by Yove on 7/4/16.
 */
public class DiaryCommentCountSelector extends TextObjectSelector {

    @Override
    public String select(Element element, Optional<String> parentId) {
        String countText = super.select(element, parentId);
        List<String> count = Splitter.on(' ').splitToList(countText);
        return count.size() == 2 ? count.get(0) : "0";
    }
}
