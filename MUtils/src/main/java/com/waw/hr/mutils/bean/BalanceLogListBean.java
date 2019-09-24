package com.waw.hr.mutils.bean;

import java.util.List;

public class BalanceLogListBean {


    /**
     * num : 10
     * p : 3
     * totalNum : 39
     * totoalPage : 4
     */

    private int num;
    private String p;
    private int totalNum;
    private int totoalPage;
    private List<BalanceLogBean> list;

    public List<BalanceLogBean> getList() {
        return list;
    }

    public void setList(List<BalanceLogBean> list) {
        this.list = list;
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

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotoalPage() {
        return totoalPage;
    }

    public void setTotoalPage(int totoalPage) {
        this.totoalPage = totoalPage;
    }
}
