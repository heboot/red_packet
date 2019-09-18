package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivitySettingBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("设置");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvAccount.setOnClickListener((v) -> {
            IntentUtils.doIntent(AccountActivity.class);
        });
    }
}
