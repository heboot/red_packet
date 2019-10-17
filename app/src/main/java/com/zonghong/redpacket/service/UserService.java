package com.zonghong.redpacket.service;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.activity.loginregister.LoginActivity;
import com.zonghong.redpacket.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;

public class UserService {

    private String token;

    private String ry_token;

    private String userId;

    private String phone;

    private String balance;

    private UserInfoBean userInfoBean;

    private boolean zhendong, shengyin, tixing;

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public boolean isZhendong() {
        return PreferencesUtils.getBoolean(MAPP.mapp, "zhendong");
    }

    public void setZhendong(boolean zhendong) {
        this.zhendong = zhendong;
        PreferencesUtils.putBoolean(MAPP.mapp,"zhendong", zhendong);
    }

    public boolean isShengyin() {
        return PreferencesUtils.getBoolean(MAPP.mapp, "shengyin");
    }

    public void setShengyin(boolean shengyin) {
        this.shengyin = shengyin;
        PreferencesUtils.putBoolean(MAPP.mapp,"shengyin", shengyin);
    }

    public boolean isTixing() {
        return PreferencesUtils.getBoolean(MAPP.mapp, "tixing");
    }

    public void setTixing(boolean tixing) {
        this.tixing = tixing;
        PreferencesUtils.putBoolean(MAPP.mapp,"tixing", tixing);
    }

    public void logout() {
        EventBus.getDefault().post(new UserEvent.LOGOUT_EVENT());
        setUserId("");
        setRy_token("");
        setToken("");
        RongIM.getInstance().logout();
        IntentUtils.doIntent(LoginActivity.class);
    }

    public String getToken() {
        if (StringUtils.isEmpty(token)) {
            return PreferencesUtils.getString(MAPP.mapp, MKey.TOKEN);
        }
        return token;
    }

    public String getRy_token() {
        if (StringUtils.isEmpty(ry_token)) {
            return PreferencesUtils.getString(MAPP.mapp, MKey.RY_TOKEN);
        }
        return ry_token;
    }

    public void setRy_token(String ry_token) {
        this.ry_token = ry_token;
        PreferencesUtils.putString(MAPP.mapp, MKey.RY_TOKEN, ry_token);
    }

    public static UserService getUserService() {
        return userService;
    }

    public static void setUserService(UserService userService) {
        UserService.userService = userService;
    }

    public void setToken(String token) {
        this.token = token;
        PreferencesUtils.putString(MAPP.mapp, MKey.TOKEN, token);
    }

    public String getUserId() {
        if (StringUtils.isEmpty(userId)) {
            return PreferencesUtils.getString(MAPP.mapp, "userId");
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        PreferencesUtils.putString(MAPP.mapp, "userId", userId);
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public String getPhone() {
        if (StringUtils.isEmpty(phone)) {
            return PreferencesUtils.getString(MAPP.mapp, "phone");
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        PreferencesUtils.putString(MAPP.mapp, "phone", phone);
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
