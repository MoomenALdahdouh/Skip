package com.example.skip.model;

import org.jetbrains.annotations.Contract;

public class Category {
    String title;
    String subTitle;
    String image;
    String background;
    public Category(String title, String subTitle, String image,String background) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
