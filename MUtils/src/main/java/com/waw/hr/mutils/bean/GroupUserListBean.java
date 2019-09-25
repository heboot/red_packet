package com.waw.hr.mutils.bean;

import java.util.List;

public class GroupUserListBean {
    /**
     * admin_id : 81
     * gUser : [{"group_id":444,"user_id":82,"admin":1,"sex":1,"nick_name":"成都","image":"http://zonghongkeji.cn/redPacket/public/upload/20190924/fcaa7d46233318e0f1f81d9c3a4e03a3.jpg"},{"group_id":444,"user_id":80,"admin":1,"sex":1,"nick_name":"青椒uu","image":"http://zonghongkeji.cn/redPacket/public/upload/20190924/a2726daa26d9081ce1a1f7a71097b9f5.JPEG"}]
     */

    private int admin_id;
    private List<GUserBean> gUser;

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public List<GUserBean> getGUser() {
        return gUser;
    }

    public void setGUser(List<GUserBean> gUser) {
        this.gUser = gUser;
    }

    public static class GUserBean {
        /**
         * group_id : 444
         * user_id : 82
         * admin : 1
         * sex : 1
         * nick_name : 成都
         * image : http://zonghongkeji.cn/redPacket/public/upload/20190924/fcaa7d46233318e0f1f81d9c3a4e03a3.jpg
         */

        private int group_id;
        private int user_id;
        private int admin;
        private int sex;
        private String nick_name;
        private String image;

        private boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
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
}
