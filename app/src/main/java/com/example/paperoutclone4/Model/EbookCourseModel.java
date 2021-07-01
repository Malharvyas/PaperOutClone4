package com.example.paperoutclone4.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EbookCourseModel implements Serializable {

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("course_iamge")
    @Expose
    private String courseIamge;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discounted_price")
    @Expose
    private String discountedPrice;
    @SerializedName("is_free")
    @Expose
    private String isFree;
    @SerializedName("validity_id")
    @Expose
    private String validityId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("modify_date")
    @Expose
    private String modifyDate;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("is_active")
    @Expose
    private String is_active;

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCourseIamge() {
        return courseIamge;
    }

    public void setCourseIamge(String courseIamge) {
        this.courseIamge = courseIamge;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getValidityId() {
        return validityId;
    }

    public void setValidityId(String validityId) {
        this.validityId = validityId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

}
