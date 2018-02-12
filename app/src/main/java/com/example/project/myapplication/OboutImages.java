package com.example.project.myapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rajkumar on 07-16-2015.
 */
public class OboutImages implements Serializable {

    String imageSource;
    String description;

    String date;
    String place;
    String title;
    int imageId;


    public OboutImages(String description, String place,String date,int imageId,String  imageSource) {
        this.description = description;
        this.imageSource = imageSource;
        this.place = place;
        this.date = date;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
