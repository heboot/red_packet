package com.waw.hr.mutils.bean;

public class ContatsFriendBean {
    /**
     * id : 12
     * my_id : 32
     * friend_id : 33
     * real_name : B
     * image :
     * chart : B
     */

    private int ID;
    private int my_id;
    private int friend_id;
    private String real_name;
    private String image;
    private String chart;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMy_id() {
        return my_id;
    }

    public void setMy_id(int my_id) {
        this.my_id = my_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }
}
