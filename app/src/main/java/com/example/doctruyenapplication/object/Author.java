package com.example.doctruyenapplication.object;

public class Author {
    int authorId;
    String authorName, authorDes, authorImg;

    public Author() {
    }

    public Author(int authorId, String authorName, String authorDes, String authorImg) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorDes = authorDes;
        this.authorImg = authorImg;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorDes() {
        return authorDes;
    }

    public void setAuthorDes(String authorDes) {
        this.authorDes = authorDes;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }
}
