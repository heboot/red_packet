package com.zonghong.redpacket.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.UserInfoBean;
import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.common.HelpActivity;
import com.zonghong.redpacket.activity.user.InfoActivity;
import com.zonghong.redpacket.activity.user.MyBankActivity;
import com.zonghong.redpacket.activity.common.SettingActivity;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
import com.zonghong.redpacket.http.HttpObserver;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.ImageUtils;
import com.zonghong.redpacket.utils.IntentUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MyFragment extends BaseFragment<FragmentMyBinding> {


    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.vBack.setVisibility(View.GONE);
        binding.includeToolbar.tvTitle.setText("我的");
    }

    @Override
    public void initData() {
        userInfo();


    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        userInfo();

        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                LogUtil.e("会话列表哦", JSON.toJSONString(conversations));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    @Override
    public void initListener() {
        binding.tvSetting.setOnClickListener((v) -> {
            IntentUtils.doIntent(SettingActivity.class);
        });
        binding.vBg.setOnClickListener((v) -> {
            IntentUtils.doIntent(InfoActivity.class);
        });
        binding.tvCard.setOnClickListener((v) -> {
            IntentUtils.doIntent(MyBankActivity.class);
        });
        binding.tvHelp.setOnClickListener((v) -> {
            IntentUtils.doIntent(HelpActivity.class);
        });
    }

    private UserInfo userInfo;

    private void userInfo() {
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                ImageUtils.showAvatar(baseBean.getData().getImg(), binding.ivAvatar);
                binding.tvName.setText(baseBean.getData().getNick_name());
                UserService.getInstance().setUserInfoBean(baseBean.getData());
                binding.tvNo.setText(baseBean.getData().getAccount_id());
                userInfo = new UserInfo(UserService.getInstance().getUserId(), baseBean.getData().getNick_name(), Uri.parse(baseBean.getData().getImg()));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                RongIM.getInstance().setMessageAttachedUserInfo(true);
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {

            }
        });
    }
}
