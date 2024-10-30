package com.example.doctruyenapplication.object;

import java.util.List;

public class Author {
    private int authorId;
    private String authorName, description, pictureLink;
    private List<Book> books;

    public Author() {
    }

    public Author(int authorId, String authorName, String description, String pictureLink) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.description = description;
        this.pictureLink = pictureLink;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
