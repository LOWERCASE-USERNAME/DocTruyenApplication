package com.example.doctruyenapplication.object;

public class Book {
    private String link, bookname,bookchap;

    public Book() {
    }

    public Book(String link, String bookname, String bookchap) {
        this.link = link;
        this.bookname = bookname;
        this.bookchap = bookchap;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookchap() {
        return bookchap;
    }

    public void setBookchap(String bookchap) {
        this.bookchap = bookchap;
    }
}
