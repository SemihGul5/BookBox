package com.example.bookbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Book {
    private int id;
    private int image;
    private String name;
    private String descrp;
    private byte[] bytes;

    public Book(int id, int image, String name, String descrp,byte[]bytes) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.descrp = descrp;
        this.bytes=bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
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


    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }


}
