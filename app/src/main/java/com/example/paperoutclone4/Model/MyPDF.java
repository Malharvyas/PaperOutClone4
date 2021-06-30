package com.example.paperoutclone4.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyPDF{

    public String lesion_id;
    public String course_id;
    public String total_question;
    public String name;
    public String pdf_url;

    public MyPDF() {
    }

    public MyPDF(String lesion_id, String course_id, String total_question, String name, String pdf_url) {
        this.lesion_id = lesion_id;
        this.course_id = course_id;
        this.total_question = total_question;
        this.name = name;
        this.pdf_url = pdf_url;
    }

    public String getLesion_id() {
        return lesion_id;
    }

    public void setLesion_id(String lesion_id) {
        this.lesion_id = lesion_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getTotal_question() {
        return total_question;
    }

    public void setTotal_question(String total_question) {
        this.total_question = total_question;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }
}
