package com.waw.hr.mutils.bean;

public class SafePrivacyBean {
    /**
     * consent : 1
     * search_phone : 1
     * search_ID : 1
     * friend_check : 0
     */

    private int consent;
    private int search_phone;
    private int search_ID;
    private int friend_check;

    public int getConsent() {
        return consent;
    }

    public void setConsent(int consent) {
        this.consent = consent;
    }

    public int getSearch_phone() {
        return search_phone;
    }

    public void setSearch_phone(int search_phone) {
        this.search_phone = search_phone;
    }

    public int getSearch_ID() {
        return search_ID;
    }

    public void setSearch_ID(int search_ID) {
        this.search_ID = search_ID;
    }

    public int getFriend_check() {
        return friend_check;
    }

    public void setFriend_check(int friend_check) {
        this.friend_check = friend_check;
    }
}
