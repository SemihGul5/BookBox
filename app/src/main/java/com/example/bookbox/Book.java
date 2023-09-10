package com.example.bookbox;

public class Book {
    private int id;
    private int image;
    private String name;
    private String writer;
    private String category;
    private String date;
    private String publisher;
    private String descrp;

    public Book(int id, int image, String name, String writer, String category, String date, String publisher, String descrp) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.writer = writer;
        this.category = category;
        this.date = date;
        this.publisher = publisher;
        this.descrp = descrp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }
}
