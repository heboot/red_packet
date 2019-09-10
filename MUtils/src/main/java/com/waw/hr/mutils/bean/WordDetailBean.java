package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class WordDetailBean implements Serializable {


    /**
     * id : 2
     * type_id : 1
     * title : allow
     * comment : 允许--给予--认可
     * example : Allow access 允许访问允许进入选--allow ally 允许结盟--allow no 一刀切
     */

    private int id;
    private int type_id;
    private String title;
    private String comment;
    private String example;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
