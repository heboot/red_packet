package com.waw.hr.mutils.bean;

import java.util.List;

public class RedpackageLogBean {
    /**
     * totalPage : 1
     * totalMoney : 48.63
     * totalCount : 1
     * nickName : 花开富贵
     * image : http://zonghongkeji.cn/redPacket/public/upload/20190925/047f004cd11324078c36cdaf03aeeaf6.jpg
     * num : 10
     * p : 1
     * list : [{"ID":2340,"money":48.63,"sign":1,"create_time":"2019-09-25 13:55:24","nick_name":"花开富贵"}]
     */

    private int totalPage;
    private double totalMoney;
    private int totalCount;
    private String nickName;
    private String image;
    private int num;
    private String p;
    private List<ListBean> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * ID : 2340
         * money : 48.63
         * sign : 1
         * create_time : 2019-09-25 13:55:24
         * nick_name : 花开富贵
         */

        private int ID;
        private double money;
        private int sign;
        private String create_time;
        private String nick_name;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
    }
}
