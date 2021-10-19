package com.example.skip.model;
import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;

public class Category {
    @Keep
    @SerializedName("title")
    String title;
    @SerializedName("subTitle")
    String subTitle;
    @SerializedName("image")
    String image;
    @SerializedName("background")
    String background;
    @SerializedName("parentCategoryId")
    String categoryId;
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

    public Category() {
    }

    public Category(String title, String subTitle, String image, String background, String categoryId
            , String status, String createAt, String createBy, String lastUpdate, String updateBy) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.background = background;
        this.categoryId = categoryId;
        this.status = status;
        this.createAt = createAt;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.updateBy = updateBy;
    }

    public Category(String title, String subTitle, String image, String background) {
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
}
