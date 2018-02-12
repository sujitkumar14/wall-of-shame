package com.example.project.myapplication;

/**
 * Created by Rajkumar on 08-13-2015.
 */
public class OboutNavigation {


    String name;
    Integer image;

    OboutNavigation(String name,Integer image)
    {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
