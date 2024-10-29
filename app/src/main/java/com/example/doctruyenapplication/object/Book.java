package com.example.doctruyenapplication.object;

public class Book {
    private String link;      // Liên kết hình ảnh của sách
    private String bookName;  // Tên sách
    private String bookChapter; // Chương sách

    // Constructor không tham số
    public Book() {
    }

    // Constructor có tham số
    public Book(String link, String bookName, String bookChapter) {
        this.link = link;
        this.bookName = bookName;
        this.bookChapter = bookChapter;
    }

    // Getter cho link
    public String getLink() {
        return link;
    }

    // Setter cho link
    public void setLink(String link) {
        this.link = link;
    }

    public String getBookName() {
        return bookName;
    }

    // Setter cho bookName
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    // Getter cho bookChapter
    public String getBookChapter() {
        return bookChapter;
    }

    // Setter cho bookChapter
    public void setBookChapter(String bookChapter) {
        this.bookChapter = bookChapter;
    }
}