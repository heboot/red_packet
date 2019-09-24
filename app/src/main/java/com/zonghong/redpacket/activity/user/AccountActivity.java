package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.databinding.ActivityAccountBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class AccountActivity extends BaseActivity<ActivityAccountBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("账号与安全");
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        binding.tvAlterPaypwd.setOnClickListener((v) -> {
            IntentUtils.doIntent(AlterPayPwdActivity.class);
        });
        binding.tvAlterPwd.setOnClickListener((v) -> {
            IntentUtils.intent2VerifyCodeActivity(CheckCodeType.PASSWORD);
        });

    }
}
