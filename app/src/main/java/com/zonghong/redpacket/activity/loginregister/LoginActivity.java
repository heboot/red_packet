package com.zonghong.redpacket.activity.loginregister;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityLoginBinding;
import com.zonghong.redpacket.utils.IntentUtils;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUI() {
        binding.qlytPhone.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytPwd.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.llytRegister.setOnClickListener((v) -> {
            IntentUtils.doIntent(RegisterActivity.class);
        });
        binding.tvForget.setOnClickListener((v) -> {
            IntentUtils.doIntent(ForgetActivity.class);
        });
    }
}
