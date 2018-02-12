package com.example.project.myapplication;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Rajkumar on 07-19-2015.
 */
public class OboutComments implements Serializable {

    String comment;
    String userName;
    int imageId;
    String  date;


    public OboutComments(String comment, String userName,String date) {
        this.comment = comment;
        this.userName = userName;
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
