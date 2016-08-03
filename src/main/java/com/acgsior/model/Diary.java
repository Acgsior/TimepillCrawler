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

    private String image;

    public static Diary newInstance(String diaryId) {
        Diary instance = new Diary();
        instance.setId(diaryId);
        return instance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Diary{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", diaryDate=").append(diaryDate);
        sb.append(", diaryTime=").append(diaryTime);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", content='").append(content.replaceAll("\n", " ")).append('\'');
        sb.append('}');
        return sb.toString();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
