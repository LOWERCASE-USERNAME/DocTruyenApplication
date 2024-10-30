package com.example.doctruyenapplication.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book {
    private int bookId;
    private String bookName;
    private String description;
    private Date uploadTime;
    private String pictureLink;
    private int status;
    private List<Genre> genres;
    private List<Chapter> chapters;
    private List<Author> authors;


    public Book() {
    }

    public Book(String pictureLink, String bookName, String chapters) {
        this.pictureLink = pictureLink;
        this.bookName = bookName;
        this.chapters = new ArrayList<>();
    }

    public Book(int bookId, String bookName, String description, Date uploadTime, String pictureLink, int status) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.description = description;
        this.uploadTime = uploadTime;
        this.pictureLink = pictureLink;
        this.status = status;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}