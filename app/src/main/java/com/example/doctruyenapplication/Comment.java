package com.example.doctruyenapplication;

public class Comment {
    private String text;
    private boolean isAuthor;

    public Comment(String text, boolean isAuthor) {
        this.text = text;
        this.isAuthor = isAuthor;
    }

    public String getText() {
        return text;
    }

    public boolean isAuthor() {
        return isAuthor;
    }
}