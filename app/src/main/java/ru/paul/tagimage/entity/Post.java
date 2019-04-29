package ru.paul.tagimage.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    @SerializedName("image")
    private Integer image;

    @SerializedName("author")
    private Author author;

    @SerializedName("caption")
    private String caption;

    @SerializedName("date")
    private Date date;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
