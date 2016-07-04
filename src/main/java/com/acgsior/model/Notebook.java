package com.acgsior.model;


import org.apache.commons.collections.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Yove on 16/07/03.
 */
public class Notebook extends Base {

    private String name;
    private String cover;

    private LocalDate begin;
    private LocalDate end;

    // private Image cover;

    public static Notebook newInstance() {
        return new Notebook();
    }

    public void setBeginEnd(List<LocalDate> dates) {
        if (CollectionUtils.isNotEmpty(dates) && dates.size() == 2) {
            begin = dates.get(0);
            end = dates.get(1);
        }
    }

    public boolean isNotebookExpired() {
        return end.compareTo(LocalDate.now()) <= 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(final String cover) {
        this.cover = cover;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(final LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(final LocalDate end) {
        this.end = end;
    }
}
