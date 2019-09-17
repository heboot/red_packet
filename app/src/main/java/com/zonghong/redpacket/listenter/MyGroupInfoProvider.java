package com.zonghong.redpacket.listenter;

import android.net.Uri;

import com.example.http.HttpClient;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupInfoBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

public class MyGroupInfoProvider implements RongIM.GroupInfoProvider {

    private Group userInfo;

    @Override
    public Group getGroupInfo(String s) {
        Map params = new HashMap<>();
        params.put(MKey.ID, s);
        HttpClient.Builder.getServer().groupInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GroupInfoBean>() {
            @Override
            public void onSuccess(BaseBean<GroupInfoBean> baseBean) {
//                userInfo = new UserInfo(s, baseBean.getData().get(0).getNick_name(), Uri.parse(baseBean.getData().get(0).getImage()));
                userInfo = new Group(s, baseBean.getData().getTitle(), Uri.parse(baseBean.getData().getImg()));
                RongIM.getInstance().refreshGroupInfoCache(userInfo);
            }

            @Override
            public void onError(BaseBean<GroupInfoBean> baseBean) {

            }
        });
        return null;
    }
}
