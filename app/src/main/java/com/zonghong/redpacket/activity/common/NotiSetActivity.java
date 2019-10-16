package com.zonghong.redpacket.activity.common;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityNotiSetBinding;

public class NotiSetActivity extends BaseActivity<ActivityNotiSetBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_noti_set;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("消息通知");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
