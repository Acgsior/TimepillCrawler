package com.acgsior.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by mqin on 7/4/16.
 */
public class Diary extends Base {

    private String content;

    private LocalDate diaryDate;

    private LocalTime diaryTime;

    private int commentCount;

    // private Image image;


    public static Diary newInstance() {
        return new Diary();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(LocalDate diaryDate) {
        this.diaryDate = diaryDate;
    }

    public LocalTime getDiaryTime() {
        return diaryTime;
    }

    public void setDiaryTime(LocalTime diaryTime) {
        this.diaryTime = diaryTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
