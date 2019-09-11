package com.zonghong.redpacket.service;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.PreferencesUtils;
import com.waw.hr.mutils.StringUtils;
import com.zonghong.redpacket.MAPP;

public class UserService {

    private String token;

    private String ry_token;

    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
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
}
