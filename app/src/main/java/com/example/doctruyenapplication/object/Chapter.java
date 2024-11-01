package com.example.doctruyenapplication.object;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int chapterId;
    private String chapterName;
    private int chapterOrder;
    private String content;
    private int bookId;
    private Book book;
    private int nextChapterId;
    private int prevChapterId;


    public Chapter() {
    }

    public Chapter(int chapterId, String chapterName, int chapterOrder, String content, int bookId) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.chapterOrder = chapterOrder;
        this.content = content;
        this.bookId = bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(int chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getNextChapterId() {
        return nextChapterId;
    }

    public void setNextChapterId(int nextChapterId) {
        this.nextChapterId = nextChapterId;
    }

    public int getPrevChapterId() {
        return prevChapterId;
    }

    public void setPrevChapterId(int prevChapterId) {
        this.prevChapterId = prevChapterId;
    }
}
