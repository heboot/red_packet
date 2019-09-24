package com.waw.hr.mutils.bean;

public class BalanceLogBean {
    /**
     * ID : 1929
     * money : 100.00
     * sign : 1
     * type : 0
     * create_time : 2019-09-20 09-34-47
     */

    private int ID;
    private String money;
    private String sign;
    private int type;
    private String create_time;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
