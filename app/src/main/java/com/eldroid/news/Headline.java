package com.eldroid.news;

public class Headline {
    private String title;
    private String content;
    private int imageResId;

    public Headline(String title, String content, int imageResId) {
        this.title = title;
        this.content = content;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getImageResId() {
        return imageResId;
    }
}

