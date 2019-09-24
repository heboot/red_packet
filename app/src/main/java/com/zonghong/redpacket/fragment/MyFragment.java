package com.zonghong.redpacket.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.http.HttpClient;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    private void userInfo() {
        HttpClient.Builder.getServer().userInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<UserInfoBean>() {
            @Override
            public void onSuccess(BaseBean<UserInfoBean> baseBean) {
                ImageUtils.showAvatar(baseBean.getData().getImg(), binding.ivAvatar);
                binding.tvName.setText(baseBean.getData().getNick_name());
                UserService.getInstance().setUserInfoBean(baseBean.getData());
                binding.tvNo.setText(baseBean.getData().getAccount_id());
            }

            @Override
            public void onError(BaseBean<UserInfoBean> baseBean) {

            }
        });
    }
}
