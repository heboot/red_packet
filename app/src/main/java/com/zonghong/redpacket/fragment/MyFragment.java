package com.zonghong.redpacket.fragment;

import android.os.Bundle;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.loginregister.LoginActivity;
import com.zonghong.redpacket.adapter.ContactsAdapter;
import com.zonghong.redpacket.base.BaseFragment;
import com.zonghong.redpacket.databinding.FragmentMyBinding;
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
    }

    @Override
    public void initData() {

    }


    @Override
    public void initListener() {
        binding.tvLogin.setOnClickListener((v) -> {
            IntentUtils.doIntent(LoginActivity.class);
        });
    }
}
