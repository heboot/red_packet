package com.zonghong.redpacket.listenter;

import android.net.Uri;

import com.example.http.HttpClient;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MyUserInfoProvider implements RongIM.UserInfoProvider {

    private UserInfo userInfo;

    @Override
    public UserInfo getUserInfo(String s) {

        Map params = new HashMap<>();
        params.put(MKey.ID, s);
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
//                userInfo = new UserInfo(s, baseBean.getData().get(0).getNick_name(), Uri.parse(baseBean.getData().get(0).getImage()));
                userInfo = new UserInfo(s, baseBean.getData().getNick_name(), Uri.parse(baseBean.getData().getImg()));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {

            }
        });

        return null;
    }
}
