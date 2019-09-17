package com.waw.hr.mutils.bean;

public class GroupInfoBean {


    /**
     * id : 190
     * title : 群聊 09-17
     * user_id : 33
     * notice :
     * img : http://zonghongkeji.cn/redPacket/public/upload/defult/group_heading.jpg
     */

    private int ID;
    private String title;
    private int user_id;
    private String notice;
    private String img;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
