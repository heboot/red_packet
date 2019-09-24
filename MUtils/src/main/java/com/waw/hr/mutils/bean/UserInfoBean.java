package com.waw.hr.mutils.bean;

public class UserInfoBean {
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

    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
