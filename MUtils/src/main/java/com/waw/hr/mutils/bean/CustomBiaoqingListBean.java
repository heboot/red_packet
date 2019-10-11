package com.waw.hr.mutils.bean;

import java.util.List;

public class CustomBiaoqingListBean {
    /**
     * current : 1
     * everyNum : 10
     * totalNum : 1
     * totalNumberOfTimes : 1
     * expressionList : [{"id":1,"user_id":94,"image":"https://zonghongkeji.cn/redPacket/public/expression/20191010/594c71ff543ad03aae8d6f5de62fd647.gif"},{"id":2,"user_id":94,"image":"https://zonghongkeji.cn/redPacket/public/expression/20191010/594c71ff543ad03aae8d6f5de62fd647.gif"}]
     */

    private int current;
    private int everyNum;
    private int totalNum;
    private int totalNumberOfTimes;
    private List<ExpressionListBean> expressionList;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getEveryNum() {
        return everyNum;
    }

    public void setEveryNum(int everyNum) {
        this.everyNum = everyNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalNumberOfTimes() {
        return totalNumberOfTimes;
    }

    public void setTotalNumberOfTimes(int totalNumberOfTimes) {
        this.totalNumberOfTimes = totalNumberOfTimes;
    }

    public List<ExpressionListBean> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<ExpressionListBean> expressionList) {
        this.expressionList = expressionList;
    }

    public static class ExpressionListBean {
        /**
         * id : 1
         * user_id : 94
         * image : https://zonghongkeji.cn/redPacket/public/expression/20191010/594c71ff543ad03aae8d6f5de62fd647.gif
         */

        private int id;
        private int user_id;
        private String image;

        private boolean isAdd;

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
