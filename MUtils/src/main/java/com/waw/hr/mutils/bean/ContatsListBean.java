package com.waw.hr.mutils.bean;

import java.util.List;

public class ContatsListBean {

    private int stayConsentNum;


    private List<ContatsChildBean> list;

    public int getStayConsentNum() {
        return stayConsentNum;
    }

    public void setStayConsentNum(int stayConsentNum) {
        this.stayConsentNum = stayConsentNum;
    }

    public List<ContatsChildBean> getList() {
        return list;
    }

    public void setList(List<ContatsChildBean> list) {
        this.list = list;
    }
}
