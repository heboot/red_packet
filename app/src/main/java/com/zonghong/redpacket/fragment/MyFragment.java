package com.zonghong.redpacket.fragment;

import android.os.Bundle;
import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.LoginActivity;
import com.zonghong.redpacket.activity.user.SettingActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
import com.zonghong.redpacket.service.UserService;
import com.zonghong.redpacket.utils.IntentUtils;

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
    }


    @Override
    public void initListener() {
        binding.tvSetting.setOnClickListener((v) -> {
            IntentUtils.doIntent(SettingActivity.class);
        });
    }
}
