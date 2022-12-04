package com.example.aithi_quanlysach;

public class Book {
    String book_name, book_what, book_why, book_creator, book_id;

    public Book() {
    }

    public Book(String book_name, String book_what, String book_why, String book_creator, String book_id) {
        this.book_name = book_name;
        this.book_what = book_what;
        this.book_why = book_why;
        this.book_creator = book_creator;
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_what() {
        return book_what;
    }

    public void setBook_what(String book_what) {
        this.book_what = book_what;
    }

    public String getBook_why() {
        return book_why;
    }

    public void setBook_why(String book_why) {
        this.book_why = book_why;
    }

    public String getBook_creator() {
        return book_creator;
    }

    public void setBook_creator(String book_creator) {
        this.book_creator = book_creator;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }
}
