package com.waw.hr.mutils.bean;

import java.io.Serializable;
import java.util.List;

public class GroupDetaiInfoBean implements Serializable {


    /**
     * groupUserInfo : [{"user_id":82,"group_name":"成都","admin":1,"image":"http://zonghongkeji.cn/redPacket/public/upload/20190924/fcaa7d46233318e0f1f81d9c3a4e03a3.jpg","sex":1},{"user_id":81,"group_name":"花开富贵","admin":3,"image":"http://zonghongkeji.cn/redPacket/public/upload/20190924/3050235a67b903e17feaaad5345b1e10.jpg","sex":2}]
     * myGInfo : {"admin":3,"nick_name":"花开富贵","reject_msg":0,"chat_top":0,"invite_confirm":0,"bannet_chat":0}
     * groupInfo : {"ID":444,"title":"花开富贵的群聊","user_id":81,"notice":"","invite_confirm":0,"bannet_chat":0,"img":"http://zonghongkeji.cn/redPacket/public/upload/defult/group_heading.jpg"}
     */

    private MyGInfoBean myGInfo;
    private GroupInfoBean groupInfo;
    private List<GroupUserInfoBean> groupUserInfo;

    public MyGInfoBean getMyGInfo() {
        return myGInfo;
    }

    public void setMyGInfo(MyGInfoBean myGInfo) {
        this.myGInfo = myGInfo;
    }

    public GroupInfoBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfoBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public List<GroupUserInfoBean> getGroupUserInfo() {
        return groupUserInfo;
    }

    public void setGroupUserInfo(List<GroupUserInfoBean> groupUserInfo) {
        this.groupUserInfo = groupUserInfo;
    }

    public static class MyGInfoBean implements Serializable {
        /**
         * admin : 3
         * nick_name : 花开富贵
         * reject_msg : 0
         * chat_top : 0
         * invite_confirm : 0
         * bannet_chat : 0
         */

        private int admin;
        private String nick_name;
        private int reject_msg;
        private int chat_top;
        private int invite_confirm;
        private int bannet_chat;

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getReject_msg() {
            return reject_msg;
        }

        public void setReject_msg(int reject_msg) {
            this.reject_msg = reject_msg;
        }

        public int getChat_top() {
            return chat_top;
        }

        public void setChat_top(int chat_top) {
            this.chat_top = chat_top;
        }

        public int getInvite_confirm() {
            return invite_confirm;
        }

        public void setInvite_confirm(int invite_confirm) {
            this.invite_confirm = invite_confirm;
        }

        public int getBannet_chat() {
            return bannet_chat;
        }

        public void setBannet_chat(int bannet_chat) {
            this.bannet_chat = bannet_chat;
        }
    }

    public static class GroupInfoBean implements Serializable {
        /**
         * ID : 444
         * title : 花开富贵的群聊
         * user_id : 81
         * notice :
         * invite_confirm : 0
         * bannet_chat : 0
         * img : http://zonghongkeji.cn/redPacket/public/upload/defult/group_heading.jpg
         */

        private int ID;
        private String title;
        private int user_id;
        private String notice;
        private int invite_confirm;
        private int bannet_chat;
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

        public int getInvite_confirm() {
            return invite_confirm;
        }

        public void setInvite_confirm(int invite_confirm) {
            this.invite_confirm = invite_confirm;
        }

        public int getBannet_chat() {
            return bannet_chat;
        }

        public void setBannet_chat(int bannet_chat) {
            this.bannet_chat = bannet_chat;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class GroupUserInfoBean implements Serializable {
        /**
         * user_id : 82
         * group_name : 成都
         * admin : 1
         * image : http://zonghongkeji.cn/redPacket/public/upload/20190924/fcaa7d46233318e0f1f81d9c3a4e03a3.jpg
         * sex : 1
         */

        private int user_id;
        private String group_name;
        private int admin;
        private String image;
        private int sex;

        private String option;

        public GroupUserInfoBean() {
        }


        public GroupUserInfoBean(String option) {
            this.option = option;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }
}
