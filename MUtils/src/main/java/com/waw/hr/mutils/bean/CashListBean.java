package com.waw.hr.mutils.bean;

import java.util.List;

public class CashListBean {

    private int totalNum;

    private int totalPage;

    private int p;

    private List<CashLogBean> list;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public List<CashLogBean> getList() {
        return list;
    }

    public void setList(List<CashLogBean> list) {
        this.list = list;
    }
}
