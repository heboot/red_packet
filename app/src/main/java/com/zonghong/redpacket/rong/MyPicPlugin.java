package com.zonghong.redpacket.rong;

import android.support.v4.app.Fragment;

import com.example.http.HttpClient;
import com.waw.hr.mutils.ToastUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zonghong.redpacket.MAPP;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imlib.model.Conversation;

public class MyPicPlugin extends ImagePlugin {
    @Override
    public void onClick(Fragment currentFragment, RongExtension extension) {
        if (extension.getConversationType() == Conversation.ConversationType.GROUP) {
            complaint(extension.getTargetId(), currentFragment, extension);
        } else {
            super.onClick(currentFragment, extension);
        }

    }


    private void complaint(String groupId, Fragment currentFragment, RongExtension extension) {
        Map params = new HashMap();
        params.put("group_id", groupId);
        HttpClient.Builder.getServer().verifyGroupStatus(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Map>() {
            @Override
            public void onSuccess(BaseBean<Map> baseBean) {

                if ( baseBean.getData().get("bannet_total")  != null && (double) baseBean.getData().get("bannet_total") == 1) {
                    ToastUtils.show(MAPP.mapp, (String) baseBean.getData().get("content"));
                } else if (baseBean.getData().get("user_bannet")  != null &&(double) baseBean.getData().get("user_bannet") == 1) {
                    ToastUtils.show(MAPP.mapp, (String) baseBean.getData().get("content"));
                } else {
                    MyPicPlugin.super.onClick(currentFragment, extension);
                }
            }

            @Override
            public void onError(BaseBean<Map> baseBean) {
                ToastUtils.show(MAPP.mapp, baseBean.getMsg());
            }
        });
    }

}
