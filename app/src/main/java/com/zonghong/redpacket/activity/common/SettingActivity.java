package com.zonghong.redpacket.activity.common;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.activity.common.AboutActivity;
import com.zonghong.redpacket.activity.user.AccountActivity;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivitySettingBinding;
import com.zonghong.redpacket.service.UserService;
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
        binding.tvAbout.setOnClickListener(view -> {
            IntentUtils.doIntent(AboutActivity.class);
        });

        binding.tvLogout.setOnClickListener((v) -> {
            UserService.getInstance().logout();
        });
        binding.tvNoti.setOnClickListener(view->{
            IntentUtils.doIntent(NotiSetActivity.class);
        });
    }
}
