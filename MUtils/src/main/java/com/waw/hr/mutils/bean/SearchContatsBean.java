package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class SearchContatsBean implements Serializable {

    private String ID;
    private String nick_name;
    private String image;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
