package com.waw.hr.mutils.bean;

import java.io.Serializable;

public class BankListBean implements Serializable {


    /**
     * ID : 2
     * user_id : 61
     * name : 招商银行
     * card : 65+2+9658489459
     * create_time : 1568860641
     */

    private int ID;
    private int user_id;
    private String bank_name;
    private String card;
    private String create_time;

    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
