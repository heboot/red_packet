package com.waw.hr.mutils.bean;

public class CashLogBean {


    /**
     * money : 100.00
     * status : 提现成功
     * bank_id : 2
     * update_time : null
     * band_name :
     * card : 9459
     */

    private String money;
    private String status;
    private int bank_id;
    private Object update_time;
    private String band_name;
    private String card;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public Object getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Object update_time) {
        this.update_time = update_time;
    }

    public String getBand_name() {
        return band_name;
    }

    public void setBand_name(String band_name) {
        this.band_name = band_name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
