package com.waw.hr.mutils.bean;

import java.util.List;

public class IntiveUserBean {
    /**
     * page : 1
     * num : 5
     * totalNum : 1
     * totalPages : 1
     * list : [{"group_id":2144,"user_id":100,"nick_name":"立彬","image":"https://zonghongkeji.cn/redPacket/public/upload/defult/defult_headimg.jpg","invite_name":"爽口大青椒啊"}]
     */

    private int page;
    private int num;
    private int totalNum;
    private int totalPages;
    private List<ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * group_id : 2144
         * user_id : 100
         * nick_name : 立彬
         * image : https://zonghongkeji.cn/redPacket/public/upload/defult/defult_headimg.jpg
         * invite_name : 爽口大青椒啊
         */

        private int group_id;
        private int user_id;
        private String nick_name;
        private String image;
        private String invite_name;
        private int wart_consent;

        public int getWart_consent() {
            return wart_consent;
        }

        public void setWart_consent(int wart_consent) {
            this.wart_consent = wart_consent;
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

        public String getInvite_name() {
            return invite_name;
        }

        public void setInvite_name(String invite_name) {
            this.invite_name = invite_name;
        }
    }
}
