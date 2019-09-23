package com.zonghong.redpacket.activity.user;

import android.view.View;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.common.CheckCodeType;
import com.zonghong.redpacket.databinding.ActivityAlterPayPwdBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class AlterPayPwdActivity extends BaseActivity<ActivityAlterPayPwdBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pay_pwd;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("修改支付密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvForget.setOnClickListener((v) -> {
            IntentUtils.intent2VerifyCodeActivity(CheckCodeType.PAY_PASSWORD);
        });
    }
}
