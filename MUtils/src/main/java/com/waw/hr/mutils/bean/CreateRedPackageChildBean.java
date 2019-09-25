package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class CreateRedPackageChildBean implements Serializable {


    /**
     * ID : 632
     * user_id : 21
     * number : 5
     */

    private int ID;
    private String user_id;
    //    private String number;
    private String group_id;
    private String from_id;

    private String image;

    private String desc;

    private String redName;

    private String money;

    private String noUserContent;

    private String userContent;

    public String getNoUserContent() {
        return noUserContent;
    }

    public void setNoUserContent(String noUserContent) {
        this.noUserContent = noUserContent;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRedName() {
        return redName;
    }

    public void setRedName(String redName) {
        this.redName = redName;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }
}
