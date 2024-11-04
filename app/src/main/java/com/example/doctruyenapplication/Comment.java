package com.example.doctruyenapplication;

public class Comment {
    private int id;
    private String username;
    private String content;
    private int storyId; // ID của truyện mà comment thuộc về

    public Comment(String username, String content) {
        this.username = username;
        this.content = content;
    }

    // Thêm constructor khác nếu cần thiết và các getter, setter

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getStoryId() {
        return storyId;
    }
}
