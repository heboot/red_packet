package com.waw.hr.mutils.bean;

import java.io.Serializable;
import java.util.List;

public class GetRedpackageBean implements Serializable {

    private String sum;

    private String name;

    private String imageUrl;

    private String des;

    private String suming;


    private int numbering;

    private int number;

    private String myGetMoney;

    public String getSuming() {
        return suming;
    }

    public void setSuming(String suming) {
        this.suming = suming;
    }

    public int getNumbering() {
        return numbering;
    }

    public void setNumbering(int numbering) {
        this.numbering = numbering;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMyGetMoney() {
        return myGetMoney;
    }

    public void setMyGetMoney(String myGetMoney) {
        this.myGetMoney = myGetMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<GetRedpackageUserBean> getList() {
        return list;
    }

    public void setList(List<GetRedpackageUserBean> list) {
        this.list = list;
    }

    private List<GetRedpackageUserBean> list;


    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
