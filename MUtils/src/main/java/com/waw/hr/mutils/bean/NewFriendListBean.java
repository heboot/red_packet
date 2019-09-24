package com.waw.hr.mutils.bean;

public class NewFriendListBean {


    /**
     * ID : 93
     * my_id : 82
     * notes :
     * friend_name : 成都
     * friend_image : /upload/20190924/fcaa7d46233318e0f1f81d9c3a4e03a3.jpg
     */

    private int ID;
    private int my_id;
    private String notes;
    private String friend_name;
    private String friend_image;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_image() {
        return friend_image;
    }

    public void setFriend_image(String friend_image) {
        this.friend_image = friend_image;
    }
}
