package com.waw.hr.mutils.model;

public class ZhuanZhangModel {

    private String ID;

    private String fromId;

    private String userContent;

    private String notUserContent;

    private String money;

    private String sum;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public String getNotUserContent() {
        return notUserContent;
    }

    public void setNotUserContent(String notUserContent) {
        this.notUserContent = notUserContent;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
