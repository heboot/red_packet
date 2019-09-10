package com.zonghong.redpacket.activity.loginregister;

import com.zonghong.redpacket.R;
import com.zonghong.redpacket.base.BaseActivity;
import com.zonghong.redpacket.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initUI() {
        binding.qlytPhone.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytPwd.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytSendcode.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
        binding.qlytCode.setRadiusAndShadow(
                getResources().getDimensionPixelOffset(R.dimen.x28)
                , getResources().getDimensionPixelOffset(R.dimen.y15), 0.30f);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
