package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    /**
     * ID : 33
     * nick_name : 青椒的同桌
     * img : http://zonghongkeji.cn/redPacket/public/upload/defult/defult_headimg.jpg
     */

    private int ID;
    private String nick_name;
    private String img;

    private String account_id;

    private int sex;

    private int sign;

    private int black;

    private int bannet_chat;

    //查看用户的信息对当前用户的关系; 0处于非好友; 1是处于待同意; 2是处于好友; 4是处于黑名单
    private int status;

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getBannet_chat() {
        return bannet_chat;
    }

    public void setBannet_chat(int bannet_chat) {
        this.bannet_chat = bannet_chat;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
