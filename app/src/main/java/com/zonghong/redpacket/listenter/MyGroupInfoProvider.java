package com.zonghong.redpacket.listenter;

import android.net.Uri;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GroupInfoBean;
import com.waw.hr.mutils.bean.MyGroupBean;
import com.waw.hr.mutils.bean.SearchContatsBean;
import com.zonghong.redpacket.activity.chat.MyGroupListActivity;
import com.zonghong.redpacket.adapter.group.MyGroupAdapter;
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
        params.put("group_id", s);


        HttpClient.Builder.getServer().gIndex(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<MyGroupBean>>() {
            @Override
            public void onSuccess(BaseBean<List<MyGroupBean>> baseBean) {
                if (baseBean.getData() != null && baseBean.getData().size() > 0) {
                    userInfo = new Group(s, baseBean.getData().get(0).getTitle(), Uri.parse(baseBean.getData().get(0).getImg()));
                    RongIM.getInstance().refreshGroupInfoCache(userInfo);
                }
            }

            @Override
            public void onError(BaseBean<List<MyGroupBean>> baseBean) {

            }
        });
        return null;
    }
}
