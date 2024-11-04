package com.example.doctruyenapplication.object;

public class BookReadHistory {

    private int readHistoryId;
    private int userId;
    private User user;
    private int bookId;
    private Book book;
    private int chapterId;
    private Chapter chapter;

    public BookReadHistory() {

    }

    public int getReadHistoryId() {
        return readHistoryId;
    }

    public void setReadHistoryId(int readHistoryId) {
        this.readHistoryId = readHistoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
