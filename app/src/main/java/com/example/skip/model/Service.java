package com.example.skip.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

public class Service {
    @Keep
    @SerializedName("title")
    String title;
    @SerializedName("subTitle")
    String subTitle;
    @SerializedName("image")
    String image;
    @SerializedName("background")
    String background;
    @SerializedName("categoryId")
    String categoryId;
    @SerializedName("subcategoryId")
    String subcategoryId;
    @SerializedName("status")
    String status;
    @SerializedName("createAt")
    String createAt;
    @SerializedName("createBy")
    String createBy;
    @SerializedName("lastUpdate")
    String lastUpdate;
    @SerializedName("updateBy")
    String updateBy;
    @SerializedName("type")
    String type;
    @SerializedName("price")
    String price;
    @SerializedName("couponStatus")
    String couponStatus;
    @SerializedName("couponAmount")
    String couponAmount;
    @SerializedName("place")
    String place;
    @SerializedName("bookingId")
    String bookingId;

    public Service() {
    }

    public Service(String title, String subTitle, String image, String background, String categoryId
            , String subcategoryId, String status, String createAt, String createBy, String lastUpdate
            , String updateBy, String type, String price, String couponStatus, String couponAmount
            , String place, String bookingId) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.background = background;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.status = status;
        this.createAt = createAt;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.updateBy = updateBy;
        this.type = type;
        this.price = price;
        this.couponStatus = couponStatus;
        this.couponAmount = couponAmount;
        this.place = place;
        this.bookingId = bookingId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
