package com.example.paperoutclone4.Model;

import java.io.Serializable;
import java.util.List;

public class MyCourse{
    public String enrole_id;
    public String course_id;
    public String price;
    public String start_date;
    public String end_date;
    public String days;
    public String sid;
    public String course_iamge;
    public String course_name;
    public String short_description;
    public String discounted_price;
    public List<MyPDF> lessions;

    public MyCourse(String enrole_id, String course_id, String price, String start_date, String end_date, String days, String sid, String course_iamge, String course_name, String short_description, String discounted_price, List<MyPDF> lessions) {
        this.enrole_id = enrole_id;
        this.course_id = course_id;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
        this.days = days;
        this.sid = sid;
        this.course_iamge = course_iamge;
        this.course_name = course_name;
        this.short_description = short_description;
        this.discounted_price = discounted_price;
        this.lessions = lessions;
    }

    public MyCourse() {
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(String discounted_price) {
        this.discounted_price = discounted_price;
    }

    public String getEnrole_id() {
        return enrole_id;
    }

    public void setEnrole_id(String enrole_id) {
        this.enrole_id = enrole_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCourse_iamge() {
        return course_iamge;
    }

    public void setCourse_iamge(String course_iamge) {
        this.course_iamge = course_iamge;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public List<MyPDF> getLessions() {
        return lessions;
    }

    public void setLessions(List<MyPDF> lessions) {
        this.lessions = lessions;
    }
}
